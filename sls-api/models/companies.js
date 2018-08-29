'use strict';
module.exports = (sequelize, DataTypes) => {
  var companies = sequelize.define('companies', {
    name: DataTypes.STRING,
    address: DataTypes.STRING,
    tell: DataTypes.STRING,
    website: DataTypes.STRING
  }, {
    underscored: true,
    timestamps: true,
    paranoid: true
  });
  companies.associate = function (models) {
    companies.hasMany(models.leads)
  };
  return companies;
};