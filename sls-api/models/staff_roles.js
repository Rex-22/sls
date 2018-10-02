'use strict';
module.exports = (sequelize, DataTypes) => {
  var staff_roles = sequelize.define('staff_roles', {
    staff_id: DataTypes.INTEGER,
    role_id: DataTypes.INTEGER
  }, {
    underscored: true,
    timestamps: true,
    paranoid: false
  });
  staff_roles.associate = function(models) {
  };
  return staff_roles;
};
