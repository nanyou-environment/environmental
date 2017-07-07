--------------------------------------------
--createAdmin
INSERT
    INTO
        ADMIN(
            ADMIN_ID
            ,NAME
            ,PHONE
            ,ACCOUNT
            ,PASSWORD
        )
    VALUES
        (
            :adminId
            ,:name
            ,:phone
            ,:account
            ,:password
        );
--------------------------------------------
--updateAdmin
UPDATE
        ADMIN
    SET
        ADMIN_ID = :adminId
        ,NAME = :name
        ,PHONE = :phone
        ,ACCOUNT = :account
        ,PASSWORD = :password
    WHERE
        ADMIN_ID = :adminId;
--------------------------------------------
--deleteAdmin
DELETE
    FROM
        ADMIN
    WHERE
        ADMIN_ID = :adminId;
--------------------------------------------
--getAdmin
SELECT
        ADMIN_ID
        ,NAME
        ,PHONE
        ,ACCOUNT
        ,PASSWORD
    FROM
        ADMIN
    WHERE
        ADMIN_ID = :adminId;
--------------------------------------------
--getAdminByAccountAndPassword
SELECT
        ADMIN_ID
        ,NAME
        ,PHONE
        ,ACCOUNT
        ,PASSWORD
    FROM
        ADMIN
    WHERE
        ACCOUNT = :account AND PASSWORD = :password