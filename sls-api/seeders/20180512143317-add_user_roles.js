'use strict';

module.exports = {
  up: (queryInterface, Sequelize) => {
    return queryInterface.bulkInsert('roles', [{
      name: 'admin',
      display_name: 'Administrator',
      description: 'Full unrestricted account',
      created_at: new Date(),
      updated_at: new Date()
    },
    {
      name: 'app-user',
      display_name: 'Application User',
      description: 'Role for users registered on the mobile application.',
      created_at: new Date(),
      updated_at: new Date()
    }], {});
  },

  down: (queryInterface, Sequelize) => {
      return queryInterface.bulkDelete('roles', null, {});
  }
};
