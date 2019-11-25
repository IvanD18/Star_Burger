create table admins(
    login text primary key not null,
    password text not null
);
INSERT INTO admins(login, password) VALUES ('IvanD18','qwer1234');
CREATE TABLE IF NOT EXISTS products (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL, price INTEGER NOT NULL CHECK (price >= 0),
    quantity INTEGER NOT NULL DEFAULT 0 CHECK (quantity >= 0),
    image_url TEXT,
    description TEXT
    );