import sqlite3

class StatsDB():

    def __init__(self, database):
        self.conn = sqlite3.connect(database)
        self.c = self.conn.cursor()

    def init_table(self, table, columns):
        description = ''
        first_col = True
        for col in columns:
            if not first_col:
                description += ', '
            if col[0] == '#':
                description += col[1:] + ' INTEGER'
            else:
                description += col + ' TEXT'
            first_col = False
        self.c.execute('CREATE TABLE IF NOT EXISTS ' + table + '(' + description + ')')

    def insert(self, table, values, norepeat=None, where=None):
        result = None
        if norepeat:
            norepeat_field = norepeat.keys()[0]
            norepeat_value = norepeat[norepeat_field]
            select_from = 'SELECT ' + norepeat_field + ' FROM ' + table
            if where:
                key = where.keys()[0]
                val = where[key]
                self.c.execute(select_from + ' WHERE ' + key + ' = ? ORDER BY time DESC LIMIT 1', (val,))
            else:
                self.c.execute(select_from + ' ORDER BY time DESC LIMIT 1')
            result = self.c.fetchone()
        if (result == None) or (norepeat_value != result[0]):
            placeholder = '?,' * len(values)
            placeholder = placeholder[:-1]
            self.c.execute('INSERT INTO ' + table + ' VALUES (' + placeholder + ')', values)

    def save(self):
        self.conn.commit()
        self.conn.close()

