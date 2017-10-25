
INSERT INTO `devices` (`id`, `name`)
VALUES
    (1,'desktop'),
    (2,'smartphone');

INSERT INTO `markets` (`id`, `region`, `lang`, `region_name`, `lang_name`)
VALUES
    (1,'US','en','','');


INSERT INTO `site_distribs` (`id`, `site_id`, `crawl_date`, `one`, `two`, `three`, `four`, `five`, `six_to_ten`, `eleven_to_twenty`, `twenty_one_to_thirty`, `thirty_one_to_forty`, `forty_one_to_fifty`, `fifty_one_to_hundred`, `non_ranking`, `created_at`, `updated_at`)
VALUES
    (1,1,20170101,100,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2017-10-24 14:30:55','2017-10-24 14:30:56');


INSERT INTO `site_search_volumes` (`id`, `site_id`, `crawl_date`, `global_aggregate_sv`, `global_average_sv`, `regional_aggregate_sv`, `regional_average_sv`, `created_at`, `updated_at`)
VALUES
    (1,1,20170101,100,NULL,NULL,NULL,'2017-10-24 14:12:41','2017-10-24 14:12:41'),
    (3,1,20170102,NULL,NULL,NULL,NULL,'2017-10-24 14:22:34','2017-10-24 14:22:34');


INSERT INTO `sites` (`id`, `user_id`, `title`, `url`, `created_at`, `updated_at`, `tracking`, `deleted`, `drop_www_prefix`, `drop_directories`, `contact_email`, `treat_non_ranking_as`, `non_ranking_value`)
VALUES
    (1,1,'lululemon.ca','lululemon.ca','2017-10-15','2017-10-15',1,0,1,0,'admin@lululemon.ca',NULL,NULL),
    (3,1,'lululemon.us','lululemon.us','2017-10-16','2017-10-16',1,0,1,0,NULL,NULL,NULL),
    (4,1,'lululemon.uk','lululemon.uk','2017-10-16','2017-10-16',1,0,1,0,NULL,NULL,NULL),
    (5,1,'lululemon.au','lululemon.au','2017-10-16','2017-10-16',1,0,1,0,NULL,NULL,NULL);


INSERT INTO `tag_distribs` (`id`, `tag_id`, `crawl_date`, `one`, `two`, `three`, `four`, `five`, `six_to_ten`, `eleven_to_twenty`, `twenty_one_to_thirty`, `thirty_one_to_forty`, `forty_one_to_fifty`, `fifty_one_to_hundred`, `non_ranking`, `created_at`, `updated_at`)
VALUES
    (1,1,20170101,100,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2017-10-24 14:47:09','2017-10-24 14:47:10');


INSERT INTO `tag_search_volumes` (`id`, `tag_id`, `crawl_date`, `global_aggregate_sv`, `global_average_sv`, `regional_aggregate_sv`, `regional_average_sv`, `created_at`, `updated_at`)
VALUES
    (1,1,20170101,NULL,NULL,NULL,NULL,'2017-10-24 14:42:34','2017-10-24 14:46:23'),
    (3,1,20170102,NULL,NULL,NULL,NULL,'2017-10-24 14:46:22','2017-10-24 14:46:22');


INSERT INTO `tag_stats` (`id`, `tag_id`, `crawl_date`, `total_keywords`, `tracked_keywords`, `ranking_keywords`, `google_ranking_keywords`, `google_base_ranking_keywords`, `google_top_ten_keywords`, `google_average`, `google_base_average`, `created_at`, `updated_at`)
VALUES
    (1,1,20170101,100,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2017-10-24 14:38:33','2017-10-24 14:38:33');


INSERT INTO `tags` (`id`, `site_id`, `tag`, `created_at`, `updated_at`, `deleted`, `report_tag`, `filter_refresh`, `backfill_id`)
VALUES
    (1,1,'Top10 Performer','2017-10-19','2017-10-24',0,0,NULL,NULL),
    (2,1,'Favorites','2017-10-19','2017-10-19',0,0,NULL,NULL),
    (3,1,'Seasonal Target','2017-10-19','2017-10-19',0,0,NULL,NULL),
    (4,1,'Region Target','2017-10-19','2017-10-19',0,0,NULL,NULL),
    (5,1,'Top Revenue','2017-10-19','2017-10-19',0,0,NULL,NULL),
    (6,1,'New Business','2017-10-19','2017-10-19',0,0,NULL,NULL),
    (7,1,'Red Ocean','2017-10-19','2017-10-19',0,0,NULL,NULL);


INSERT INTO `user_apis` (`id`, `user_id`, `api_key`, `ips`, `count`, `api_limit`, `created_at`, `updated_at`, `deleted`)
VALUES
    (1,1,'a9ed7429eb36a6335b714ecabfa1f84dccafce1b','',0,1000,NULL,NULL,0);


INSERT INTO `users` (`id`, `login`, `email`, `deleted_at`, `updated_at`, `created_at`, `deleted`, `password`, `nicename`)
VALUES
    (1,'mjkim','mjkim@gmail.com',NULL,NULL,NULL,0,'mjkim','');


