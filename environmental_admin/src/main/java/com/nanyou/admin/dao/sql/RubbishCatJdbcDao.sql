--------------------------------------------
--createRubbishCat
INSERT
    INTO
        RUBBISH_CAT(
            CAT_ID
            ,NAME
            ,DESC
        )
    VALUES
        (
            :catId
            ,:name
            ,: DESC
        );
--------------------------------------------
--updateRubbishCat
UPDATE
        RUBBISH_CAT
    SET
        CAT_ID = :catId
        ,NAME = :name
        ,DESC = : DESC
    WHERE
        CAT_ID = :catId;
--------------------------------------------
--deleteRubbishCat
DELETE
    FROM
        RUBBISH_CAT
    WHERE
        CAT_ID = :catId;
--------------------------------------------
--getRubbishCat
SELECT
        CAT_ID
        ,NAME
        ,DESC
    FROM
        RUBBISH_CAT
    WHERE
        CAT_ID = :catId;
