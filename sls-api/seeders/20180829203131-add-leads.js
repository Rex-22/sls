'use strict';

module.exports = {
  up: (queryInterface, Sequelize) => {
      return queryInterface.bulkInsert('leads', [{
        first_name: 'John',
        last_name: 'Doe',
        email: 'jonh@mail.com',
        cell: '082 728 4587',
        tell: '051 328 5124',
        date_added: new Date(),
        staff_id: '1',
        company_id: '1',
        created_at: new Date(),
        updated_at: new Date()
      }], {});
  },

  down: (queryInterface, Sequelize) => {
    return queryInterface.bulkDelete('leads', null, {});
  }
};
