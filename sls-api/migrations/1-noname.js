'use strict';

var Sequelize = require('sequelize');

/**
 * Actions summary:
 *
 * createTable "companies", deps: []
 * createTable "roles", deps: []
 * createTable "staffs", deps: []
 * createTable "leads", deps: [companies, staffs]
 * createTable "staff_roles", deps: [staffs, roles]
 *
 **/

var info = {
    "revision": 1,
    "name": "noname",
    "created": "2018-10-02T20:56:38.021Z",
    "comment": ""
};

var migrationCommands = [{
        fn: "createTable",
        params: [
            "companies",
            {
                "id": {
                    "type": Sequelize.INTEGER,
                    "autoIncrement": true,
                    "primaryKey": true,
                    "allowNull": false
                },
                "name": {
                    "type": Sequelize.STRING
                },
                "address": {
                    "type": Sequelize.STRING
                },
                "tell": {
                    "type": Sequelize.STRING
                },
                "website": {
                    "type": Sequelize.STRING
                },
                "created_at": {
                    "type": Sequelize.DATE,
                    "allowNull": false
                },
                "updated_at": {
                    "type": Sequelize.DATE,
                    "allowNull": false
                }
            },
            {}
        ]
    },
    {
        fn: "createTable",
        params: [
            "roles",
            {
                "id": {
                    "type": Sequelize.INTEGER,
                    "autoIncrement": true,
                    "primaryKey": true,
                    "allowNull": false
                },
                "name": {
                    "type": Sequelize.STRING
                },
                "display_name": {
                    "type": Sequelize.STRING
                },
                "description": {
                    "type": Sequelize.STRING
                },
                "created_at": {
                    "type": Sequelize.DATE,
                    "allowNull": false
                },
                "updated_at": {
                    "type": Sequelize.DATE,
                    "allowNull": false
                }
            },
            {}
        ]
    },
    {
        fn: "createTable",
        params: [
            "staffs",
            {
                "id": {
                    "type": Sequelize.INTEGER,
                    "autoIncrement": true,
                    "primaryKey": true,
                    "allowNull": false
                },
                "staff_num": {
                    "type": Sequelize.STRING
                },
                "username": {
                    "type": Sequelize.STRING,
                    "unique": true
                },
                "password": {
                    "type": Sequelize.STRING
                },
                "email": {
                    "type": Sequelize.STRING,
                    "validate": {
                        "isEmail": {
                            "msg": "This is an invallid email"
                        }
                    },
                    "unique": true
                },
                "fcm_token": {
                    "type": Sequelize.STRING
                },
                "remember_token": {
                    "type": Sequelize.TEXT
                },
                "created_at": {
                    "type": Sequelize.DATE,
                    "allowNull": false
                },
                "updated_at": {
                    "type": Sequelize.DATE,
                    "allowNull": false
                }
            },
            {}
        ]
    },
    {
        fn: "createTable",
        params: [
            "leads",
            {
                "id": {
                    "type": Sequelize.INTEGER,
                    "autoIncrement": true,
                    "primaryKey": true,
                    "allowNull": false
                },
                "first_name": {
                    "type": Sequelize.STRING
                },
                "last_name": {
                    "type": Sequelize.STRING
                },
                "email": {
                    "type": Sequelize.STRING,
                    "validate": {
                        "isEmail": {
                            "msg": "This is an invallid email"
                        }
                    },
                    "unique": true
                },
                "cell": {
                    "type": Sequelize.STRING,
                    "validate": {
                        "isNumeric": {
                            "msg": "Cell number can only be numeric"
                        }
                    }
                },
                "tell": {
                    "type": Sequelize.STRING,
                    "validate": {
                        "isNumeric": {
                            "msg": "Tell number can only be numeric"
                        }
                    }
                },
                "date_added": {
                    "type": Sequelize.DATE
                },
                "created_at": {
                    "type": Sequelize.DATE,
                    "allowNull": false
                },
                "updated_at": {
                    "type": Sequelize.DATE,
                    "allowNull": false
                },
                "company_id": {
                    "type": Sequelize.INTEGER,
                    "onUpdate": "CASCADE",
                    "onDelete": "CASCADE",
                    "references": {
                        "model": "companies",
                        "key": "id"
                    },
                    "allowNull": true
                },
                "staff_id": {
                    "type": Sequelize.INTEGER,
                    "onUpdate": "CASCADE",
                    "onDelete": "SET NULL",
                    "references": {
                        "model": "staffs",
                        "key": "id"
                    },
                    "allowNull": true
                }
            },
            {}
        ]
    },
    {
        fn: "createTable",
        params: [
            "staff_roles",
            {
                "staff_id": {
                    "type": Sequelize.INTEGER,
                    "unique": "staff_roles_staff_id_role_id_unique",
                    "onUpdate": "CASCADE",
                    "onDelete": "CASCADE",
                    "references": {
                        "model": "staffs",
                        "key": "id"
                    },
                    "primaryKey": true
                },
                "role_id": {
                    "type": Sequelize.INTEGER,
                    "unique": "staff_roles_staff_id_role_id_unique",
                    "onUpdate": "CASCADE",
                    "onDelete": "CASCADE",
                    "references": {
                        "model": "roles",
                        "key": "id"
                    },
                    "primaryKey": true
                },
                "created_at": {
                    "type": Sequelize.DATE,
                    "allowNull": false
                },
                "updated_at": {
                    "type": Sequelize.DATE,
                    "allowNull": false
                }
            },
            {}
        ]
    }
];

module.exports = {
    pos: 0,
    up: function(queryInterface, Sequelize)
    {
        var index = this.pos;
        return new Promise(function(resolve, reject) {
            function next() {
                if (index < migrationCommands.length)
                {
                    let command = migrationCommands[index];
                    console.log("[#"+index+"] execute: " + command.fn);
                    index++;
                    queryInterface[command.fn].apply(queryInterface, command.params).then(next, reject);
                }
                else
                    resolve();
            }
            next();
        });
    },
    info: info
};
