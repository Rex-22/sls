'use strict';

var Sequelize = require("sequelize");
var models = require("../models");
var helper = require("../classes/helpers");
var _ = require('lodash');

/* REGISTER */
(exports.register = (req, res, next) => {
    if (req.body.username && req.body.password) {
        models.roles.findOne({where: {name: "app-user"} }).then((role)=> {
                models.staff
                .create({
                    email: req.body.email,
                    username: req.body.username,
                    password: req.body.password,
                    fcm_token: req.body.fcm_token
                }).then( (staff) => {
                    staff.setRoles(role).then( (result) => {
                        staff.setToken().then( (staff) => {
                            res.json(_.pick(
                                staff,
                                "id",
                                "email",
                                "username",
                                "remember_token"
                            ));
                        });
                    });
                });
            }).catch(Sequelize.ValidationError, (err) => {
                var result = {};
                result.errors = helper.PrettyPrint(err);
                res.status(422).json(result);
            }).catch( (err)=> {
                res.status(500).json(err);
            });
    } else {
        res.json({ error: "Requires username and password" }).status(422);
    }
}),
/* AUTH */
(exports.authenticate = (req, res, next) => {
    models.staff.findOne({
        where: {
            [Sequelize.Op.or]: [
                { username: req.body.username },
                { email: req.body.username }
            ]
        }
    }).then( (user) => {
        if(user != null){
            user.comparePassword(req.body.password).then(() => {
                user.setToken().then((user) => {
                    res.json(_.pick(user, "id", "remember_token"));
                  }).catch(err => {
                    console.log(err);
                  });
              }).catch(function(err) {
                res.status(403)
                  .json({ error: err || "Incorrect user password." });
              });
        } else { 
            res.status(404).json({ error: "User not found" });
        }
    })
});