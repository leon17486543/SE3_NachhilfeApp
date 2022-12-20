INSERT INTO assignment (id, owner, subject, name, description, deleted) OVERRIDING SYSTEM VALUE
VALUES (Convert('67de3f07-f7ab-4359-a5a8-360827690e9b',uuid),
        Convert('dd5ea7ff-b60c-40fe-a1fa-120ec996b5b5',uuid),
        Convert('7de99560-eaf5-442e-a806-ba3bdbb7dd27',uuid),
        'testAssignment',
        'testAssignment Description',
        false);