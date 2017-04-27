# -*- coding: utf-8 -*-

# test basic sqlite interactions
# based on http://qiita.com/mas9612/items/a881e9f14d20ee1c0703
# cf also https://docs.python.org/3/library/sqlite3.html

import sqlite3

dbname = "sqlite-dbs/yktest.sqlite3"

conn = sqlite3.connect(dbname)
conn.row_factory = sqlite3.Row
c = conn.cursor()

# drop test table if it exists for testing purposes
c.execute("DROP TABLE IF EXISTS pytest")

# test execute
create_table = "create table pytest (id INTEGER PRIMARY KEY NOT NULL, name VARCHAR(64))"
c.execute(create_table)

# test setting variables into sql with ? (placeholder) values
sql = "insert into pytest (id, name) values (?,?)"
pydata = (1, "Name-A")
c.execute(sql, pydata)

# test above with multiple values using executemany
pydatalist = [
    (3, "Name-B"),
    (7, "Name-V"),
    (11, "Name-C")
]
c.executemany(sql, pydatalist)
conn.commit()

# test :name placeholders
sql = "insert into pytest (id, name) values (:id, :name)"
pydatadict = {"id": 123, "name": "Name-名前"}
c.execute(sql, pydatadict)
conn.commit()

# test select and see
select_sql = "select * from pytest"
res = c.execute(select_sql)

print("full data is:")
print(res)

print("data by rows:")
ret = []
ret2 = []
for row in res:
    ret.append(tuple(row))
    print(row.keys())
    print(tuple(row))
    ret2.append(dict(zip(row.keys(), tuple(row))))

print("data packed for return:")
print(ret)

print("list of dicts: ")
print(ret2)

# drop test table if it exists at end of test
c.execute("DROP TABLE IF EXISTS pytest")

# make sure to close any open connections
conn.close()
