'use strict';
module.exports = (sequelize, DataTypes) => {
  var role = sequelize.define('roles', {
    name: DataTypes.STRING,
    display_name: DataTypes.STRING,
    description: DataTypes.STRING
  }, {
    underscored: true,
    timestamps: true,
    deletedAt: false,
    paranoid: false,
  });
  role.associate = function(models) {
    role.belongsToMany(models.staff,  {
      through: models.staff_roles
    });
  };
  return role;
};
