CREATE TABLE T_USER_REQUEST (
                                URQ_ID_C VARCHAR(36) NOT NULL PRIMARY KEY,
                                URQ_EMAIL_C VARCHAR(100) NOT NULL UNIQUE,
                                URQ_NAME_C VARCHAR(50),
                                URQ_MESSAGE_C TEXT,
                                URQ_STATUS_C VARCHAR(20) NOT NULL DEFAULT 'PENDING',
                                URQ_CREATEDATE_D TIMESTAMP NOT NULL
);

-- Update the database version
update T_CONFIG set CFG_VALUE_C = '32' where CFG_ID_C = 'DB_VERSION';
