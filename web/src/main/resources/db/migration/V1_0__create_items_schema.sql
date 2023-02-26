CREATE TABLE IF NOT EXISTS `items` (

    `id` identity,
    `name` varchar(255),
    `registered_on` date,
    `sell_by` date,
    `quality` integer

);