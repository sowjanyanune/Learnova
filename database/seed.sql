USE learnova_db;

SET @admin_id = (SELECT MIN(id) FROM admins);

INSERT INTO missions (title, description, category, difficulty, option_a, option_b, option_c, option_d, correct_answer, xp_reward, created_by) VALUES
('Java entry point', 'Which method is the conventional entry point of a standalone Java application?', 'Java', 'Easy', 'main(String[] args)', 'start()', 'run()', 'execute()', 'main(String[] args)', 50, @admin_id),
('Primitive data types', 'Which of the following is a primitive data type in Java?', 'Java', 'Easy', 'String', 'ArrayList', 'int', 'Integer', 'int', 50, @admin_id),
('Java inheritance', 'Which keyword is used by a Java class to inherit from another class?', 'Java', 'Medium', 'implements', 'extends', 'inherits', 'instanceof', 'extends', 75, @admin_id),
('SQL data retrieval', 'Which SQL statement is used to retrieve data from a table?', 'SQL', 'Easy', 'DELETE', 'SELECT', 'UPDATE', 'INSERT', 'SELECT', 50, @admin_id),
('Filtering SQL rows', 'Which clause filters rows before grouping in a SELECT statement?', 'SQL', 'Medium', 'ORDER BY', 'GROUP BY', 'WHERE', 'HAVING', 'WHERE', 75, @admin_id),
('Relational joins', 'Which SQL join returns matching rows from both joined tables?', 'SQL', 'Hard', 'CROSS JOIN', 'FULL OUTER JOIN', 'INNER JOIN', 'LEFT JOIN', 'INNER JOIN', 100, @admin_id),
('Stack behavior', 'What does LIFO mean when describing a stack?', 'DSA', 'Easy', 'Last In, First Out', 'Lowest Index, First Out', 'Last In, Fast Output', 'Linked Items, Fixed Order', 'Last In, First Out', 50, @admin_id),
('Binary search', 'What is the average time complexity of binary search on a sorted array?', 'DSA', 'Medium', 'O(n)', 'O(log n)', 'O(n log n)', 'O(1)', 'O(log n)', 75, @admin_id),
('Breadth-first traversal', 'Which data structure is commonly used for breadth-first search?', 'DSA', 'Hard', 'Stack', 'Queue', 'Heap', 'Binary search tree', 'Queue', 100, @admin_id);
