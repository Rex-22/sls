'use strict';

var Sequelize = require('sequelize');

/**
 * Actions summary:
 *
 * createTable "companies", deps: []
 * addColumn "companyId" to table "leads"
 *
 **/

var info = {
    "revision": 3,
    "name": "noname",
    "created": "2018-08-29T20:28:25.519Z",
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
                "createdAt": {
                    "type": Sequelize.DATE,
                    "allowNull": false
                },
                "updatedAt": {
                    "type": Sequelize.DATE,
                    "allowNull": false
                }
            },
            {}
        ]
    },
    {
        fn: "addColumn",
        params: [
            "leads",
            "companyId",
            {
                "type": Sequelize.INTEGER,
                "onUpdate": "CASCADE",
                "onDelete": "SET NULL",
                "references": {
                    "model": "companies",
                    "key": "id"
                },
                "allowNull": true
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
