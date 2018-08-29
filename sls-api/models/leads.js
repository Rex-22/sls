'use strict';
module.exports = (sequelize, DataTypes) => {
  var leads = sequelize.define('leads', {
    first_name: DataTypes.STRING,
    last_name: DataTypes.STRING,
    email: {
      type: DataTypes.STRING,
      unique: true,
      validate: {
        isEmail: {
          msg: 'This is an invallid email'
        }
      }
    },
    cell: {
      type: DataTypes.STRING,
      validate: {
        isNumeric:{
          msg: 'Cell number can only be numeric'
        }
      }
    },
    tell: {
      type: DataTypes.STRING,
      validate: {
        isNumeric:{
          msg: 'Tell number can only be numeric'
        }
      }
    },
    date_added: DataTypes.DATE
  }, {
    underscored: true,
    timestamps: true,
    paranoid: true
  });
  leads.associate = function(models) {
    leads.belongsTo(models.staff);
  };
  return leads;
};