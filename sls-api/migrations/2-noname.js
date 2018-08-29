'use strict';

var Sequelize = require('sequelize');

/**
 * Actions summary:
 *
 * createTable "leads", deps: [staffs]
 * changeColumn "email" on table "staffs"
 *
 **/

var info = {
    "revision": 2,
    "name": "noname",
    "created": "2018-08-29T20:13:44.788Z",
    "comment": ""
};

var migrationCommands = [{
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
                "firstName": {
                    "type": Sequelize.STRING
                },
                "lastName": {
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
                    "type": Sequelize.STRING
                },
                "tell": {
                    "type": Sequelize.STRING
                },
                "dateAdded": {
                    "type": Sequelize.DATE
                },
                "compId": {
                    "type": Sequelize.INTEGER
                },
                "createdAt": {
                    "type": Sequelize.DATE,
                    "allowNull": false
                },
                "updatedAt": {
                    "type": Sequelize.DATE,
                    "allowNull": false
                },
                "staffId": {
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
        fn: "changeColumn",
        params: [
            "staffs",
            "email",
            {
                "type": Sequelize.STRING,
                "validate": {
                    "isEmail": {
                        "msg": "This is an invallid email"
                    }
                },
                "unique": true
            }
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
