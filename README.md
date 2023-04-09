# woowaVacation-Level1

### DDL
```sql
CREATE TABLE game_turn (
  turn VARCHAR(255) NOT NULL
);

CREATE TABLE piece_state (
  piece_name VARCHAR(255) NOT NULL,
  piece_color VARCHAR(255) NOT NULL,
  piece_row INT NOT NULL,
  piece_column INT NOT NULL
);
```
