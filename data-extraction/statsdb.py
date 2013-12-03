import sqlite3

class StatsDB():

    def __init__(self, database):
        self.conn = sqlite3.connect(database)
        self.c = self.conn.cursor()
        self.c.execute('CREATE TABLE IF NOT EXISTS stats (name TEXT, lines INTEGER, time TEXT)')

    def insert(self, name, lines, time):
        self.c.execute("SELECT lines FROM stats WHERE name = ? ORDER BY time DESC LIMIT 1", (name,))
        result = self.c.fetchone()
        if (result == None) or (lines != result[0]):
            self.c.execute('INSERT INTO stats VALUES (?, ?, ?)', (name, lines, time))

    def save(self):
        self.conn.commit()
        self.conn.close()

