'use strict';

module.exports = {
  up: (queryInterface, Sequelize) => {
      return queryInterface.bulkInsert('companies', [{
        name: 'Alex Ltd.',
        address: '18 Harvertroad, Tempie, Free State',
        tell: '051 425 2875',
        website: 'www.alex.com',
        created_at: new Date(),
        updated_at: new Date()
      }], {});
  },

  down: (queryInterface, Sequelize) => {
    return queryInterface.bulkDelete('leads', null, {});
  }
};
