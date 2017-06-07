--------------------------------------------
--createRubbish
INSERT
    INTO
        RUBBISH(
            RUBBISH_ID
            ,CAT_ID
            ,NAME
            ,ICON
            ,`DESC`
        )
    VALUES
        (
            :rubbishId
            ,:catId
            ,:name
            ,:icon
            ,:desc
        );
--------------------------------------------
--updateRubbish
UPDATE
        RUBBISH
    SET
        RUBBISH_ID = :rubbishId
        ,CAT_ID = :catId
        ,NAME = :name
        ,ICON = :icon
        ,`DESC` = :desc
    WHERE
        RUBBISH_ID = :rubbishId;
--------------------------------------------
--deleteRubbish
DELETE
    FROM
        RUBBISH
    WHERE
        RUBBISH_ID = :rubbishId;
--------------------------------------------
--getRubbish
SELECT
        RUBBISH_ID
        ,CAT_ID
        ,NAME
        ,ICON
        ,`DESC`
    FROM
        RUBBISH
    WHERE
        RUBBISH_ID = :rubbishId;
--------------------------------------------
--listRubbishByCatId
SELECT
        RUBBISH_ID
        ,CAT_ID
        ,NAME
        ,ICON
        ,`DESC`
    FROM
        RUBBISH
    WHERE
        CAT_ID = :catId;