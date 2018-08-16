'use strict';
const bcrypt = require('bcrypt-nodejs');
const jwt = require('jwt-simple');
var _ = require('lodash');

module.exports = (sequelize, DataTypes) => {
  var staff = sequelize.define('staff', {
    staff_num: DataTypes.STRING,
    username: {
      type: DataTypes.STRING,
      unique: true
    },
    password: DataTypes.STRING,
    email: {
      type: DataTypes.STRING,
      unique: true,
      validate: {
        isEmail: {
          msg: 'This is an invallid username'
        }
      }
    },
    fcm_token: DataTypes.STRING,
    remember_token: DataTypes.TEXT
  }, {
    underscored: true,
      timestamps: true,
      paranoid: true,
      hooks: {
        beforeSave: (staff, options) => {
          return new Promise((resolve, reject) => {
            if (staff.changed('password')) {
              var salt = bcrypt.genSaltSync(10);
              var hash = bcrypt.hashSync(staff.password, salt);
              staff.password = hash;
            }
            resolve();
          });
        }
      }
  });
  staff.associate = (models) => {
    staff.belongsToMany(models.roles, {
      onDelete: 'CASCADE',
      through: models.staff_roles
    });
  };

  staff.prototype.comparePassword = function (pw) {
    return new Promise((resolve, reject) => {
      bcrypt.compare(pw, this.password, (err, result) => {
        if (result) {
          resolve();
        } else {
          reject(err);
        }
      });
    });
  };
  
  staff.prototype.setToken = function () {
    return new Promise((resolve, reject) => {

      this.getRoles().then((roles) => {
        let payload = _.pick(this, 'id', 'email');
        payload.iss = 'SLS';
        payload.aud = 'App Users';
        payload.sub = 'Access Token';
        payload.roles = roles.map(a => a.name);
        payload.exp = new Date().setTime(new Date().getTime() + (12 * 60 * 60 * 1000));
        payload.iat = new Date().getTime();

        var token = jwt.encode(payload, 'iaarb-naj-sls');
        this.update({ remember_token: token }).then( (staff) => {
          resolve(staff);
        });
      });
    });
  }

  return staff;
};