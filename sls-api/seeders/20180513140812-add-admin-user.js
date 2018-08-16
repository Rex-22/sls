'use strict';

module.exports = {
  up: (queryInterface, Sequelize) => {
    return queryInterface.rawSelect('roles', {
      where: {
        name: 'admin'
      },
    }, ['id']).then(function(role) {
      return queryInterface.bulkInsert('staffs', [{
        email: 'admin@mail.com',
        username: "admin",
        password: '$2a$10$EerP6HLHWBrDMiaedKdkEeV5hBbtDbHUi0era12ImMkUGLw5SwMc.', //password
        created_at: new Date(),
        updated_at: new Date()
      }], {}).then(() => {
        return queryInterface.rawSelect('staffs', {
          where: {
            email: 'admin@mail.com'
          },
        }, ['id']).then((staff)=>{
          return queryInterface.bulkInsert('staff_roles',[{
            staff_id: staff,
            role_id: role,
            created_at: new Date(),
            updated_at: new Date()
          }], {});
        });
      });
    });
  },
  down: (queryInterface, Sequelize) => {
      return queryInterface.bulkDelete('staff_roles', null, {}).then(() => {
        return queryInterface.bulkDelete('staffs', null, {});
      });
  }
};
