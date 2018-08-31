'use strict';

var Sequelize = require("sequelize");
var models = require("../models");
var helper = require("../classes/helpers");
var _ = require('lodash');

/* GET */
(exports.get = (req, res, next) => {
    models.leads.findOne({
        where: {
            id: req.params.id
        },
        attributes: {
            exclude: helper.time_stamps
        },
        include: [{
            model: models.staff,
            attributes: {
                exclude: helper.time_stamps.concat(['remember_token', 'fcm_token', 'password'])
            },
        }, {
            model: models.companies,
            attributes: {
                exclude: helper.time_stamps
            }
        }]
    }).then((lead) => {
        if (lead != null) {
            res.status(200).json(lead)
        } else {
            res.status(404)
                .json('Lead not found')
        }
    })
}),
    /* LIST */
    (exports.list = (req, res, next) => {
        let limit = parseInt(req.query.pageSize, 10);
        let offset = 0;
        models.leads
            .count().then((count) => {
                let page = parseInt(req.query.page, 10);
                offset = limit * (page - 1);

                models.leads.findAll({
                    limit: limit || null,
                    offset: offset || null,
                }).then(results => {
                    if (!limit)
                        return res.status(200).json(results);
                    else
                        return res.status(200)
                            .json({
                                results: results,
                                count: count
                            });
                }).catch((err) => {
                    return res.status(500).json(ex);
                })

            }).catch((err) => {
                return res.status(500).json(ex);
            })
    }),
    /* CREATE */
    (exports.create = (req, res, next) => {
        models.leads.create(req.body).then((lead) => {
            res.status(201).json(lead)
        }).catch(Sequelize.ValidationError, (err) => {
            var result = {};
            result.errors = helper.PrettyPrint(err);
            res.status(422).json(result);
        }).catch(function (err) {
            res.status(500).json(err);
        });
    }),
    /* UPDATE */
    (exports.update = function (req, res, next) {
        models.leads
            .findOne({
                where: {
                    id: req.params.id
                }
            }).then((model) => {
                if (model) {
                    model.update(req.body).then((result) => {
                        res.status(200).json(_.pick(result, "id"))
                    }).catch(Sequelize.ValidationError, (err) => {
                        var result = {};
                        result.message = helper.PrettyPrint(err);
                        res.status(422).json(result);
                    }).catch((err) => {
                        res.status(500).json(err);
                    });
                } else {
                    res.status(404).send();
                }
            });
    }),
    /* DELLETE */
    (exports.delete = function (req, res, next) {
        models.leads.destroy({
            where: {
                id: req.params.id
            }
        });
        res.status(202).send();
    });