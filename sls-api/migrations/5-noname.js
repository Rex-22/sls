'use strict';

var Sequelize = require('sequelize');

/**
 * Actions summary:
 *
 * removeColumn "createdAt" from table "leads"
 * removeColumn "updatedAt" from table "companies"
 * removeColumn "updatedAt" from table "leads"
 * removeColumn "createdAt" from table "companies"
 * removeColumn "staffId" from table "leads"
 * removeColumn "companyId" from table "leads"
 * addColumn "created_at" to table "companies"
 * addColumn "updated_at" to table "companies"
 * addColumn "deleted_at" to table "companies"
 * addColumn "staff_id" to table "leads"
 * addColumn "company_id" to table "leads"
 * addColumn "deleted_at" to table "leads"
 * addColumn "updated_at" to table "leads"
 * addColumn "created_at" to table "leads"
 *
 **/

var info = {
    "revision": 5,
    "name": "noname",
    "created": "2018-08-29T20:51:39.555Z",
    "comment": ""
};

var migrationCommands = [{
        fn: "removeColumn",
        params: ["leads", "createdAt"]
    },
    {
        fn: "removeColumn",
        params: ["companies", "updatedAt"]
    },
    {
        fn: "removeColumn",
        params: ["leads", "updatedAt"]
    },
    {
        fn: "removeColumn",
        params: ["companies", "createdAt"]
    },
    {
        fn: "removeColumn",
        params: ["leads", "staffId"]
    },
    {
        fn: "removeColumn",
        params: ["leads", "companyId"]
    },
    {
        fn: "addColumn",
        params: [
            "companies",
            "created_at",
            {
                "type": Sequelize.DATE,
                "allowNull": false
            }
        ]
    },
    {
        fn: "addColumn",
        params: [
            "companies",
            "updated_at",
            {
                "type": Sequelize.DATE,
                "allowNull": false
            }
        ]
    },
    {
        fn: "addColumn",
        params: [
            "companies",
            "deleted_at",
            {
                "type": Sequelize.DATE
            }
        ]
    },
    {
        fn: "addColumn",
        params: [
            "leads",
            "staff_id",
            {
                "type": Sequelize.INTEGER,
                "onUpdate": "CASCADE",
                "onDelete": "SET NULL",
                "references": {
                    "model": "staffs",
                    "key": "id"
                },
                "allowNull": true
            }
        ]
    },
    {
        fn: "addColumn",
        params: [
            "leads",
            "company_id",
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
    },
    {
        fn: "addColumn",
        params: [
            "leads",
            "deleted_at",
            {
                "type": Sequelize.DATE
            }
        ]
    },
    {
        fn: "addColumn",
        params: [
            "leads",
            "updated_at",
            {
                "type": Sequelize.DATE,
                "allowNull": false
            }
        ]
    },
    {
        fn: "addColumn",
        params: [
            "leads",
            "created_at",
            {
                "type": Sequelize.DATE,
                "allowNull": false
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
