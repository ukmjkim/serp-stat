DROP TABLE `alerts` IF EXISTS;
CREATE TABLE `alerts` (
  `id` bigint(20) NOT NULL,
  `alert` varchar(255) NOT NULL,
  `created_at` date NOT NULL,
  `updated_at` date NOT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE `annotations` IF EXISTS;
CREATE TABLE `annotations` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `annotation_type` varchar(255) NOT NULL,
  `note` text NOT NULL,
  `updater_user_id` int(11) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `annotateable_type` varchar(255) DEFAULT NULL,
  `annotateable_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE `backfills` IF EXISTS;
CREATE TABLE `backfills` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `backfillable_type` varchar(255) NOT NULL,
  `backfillable_id` int(11) NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime DEFAULT NULL,
  `from_date` date NOT NULL,
  `to_date` date NOT NULL,
  `contact_email` varchar(255) DEFAULT NULL,
  `progress` decimal(5,2) DEFAULT '0.00',
  `finished` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
);

DROP TABLE `bad_parses` IF EXISTS;
CREATE TABLE `bad_parses` (
  `keyword_id` int(11) NOT NULL,
  `created_at` date NOT NULL
);


DROP TABLE `bulk_jobs` IF EXISTS;
CREATE TABLE `bulk_jobs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(36) NOT NULL,
  `api` varchar(255) DEFAULT NULL,
  `job` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL DEFAULT 'NotStarted',
  `vars` longtext,
  `output` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `params_file_name` varchar(255) DEFAULT NULL,
  `requested_date` datetime DEFAULT NULL,
  `expected_count` int(11) DEFAULT NULL,
  `actual_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


DROP TABLE `calendar` IF EXISTS;
CREATE TABLE `calendar` (
  `c_date` date DEFAULT NULL
);


DROP TABLE `crawl_keywords` IF EXISTS;
CREATE TABLE `crawl_keywords` (
  `keyword_id` int(11) NOT NULL,
  `created_at` date NOT NULL
);



DROP TABLE `crawl_states` IF EXISTS;
CREATE TABLE `crawl_states` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `engine` varchar(255) NOT NULL,
  `crawl_keywords_count` bigint(20) NOT NULL,
  `engine_count` bigint(20) NOT NULL,
  `unknown_count` bigint(20) DEFAULT NULL,
  `percentage_done` decimal(7,4) DEFAULT NULL,
  `unknown_percentage` decimal(7,4) DEFAULT NULL,
  `crawl_date` date NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
);


DROP TABLE `devices` IF EXISTS;
CREATE TABLE `devices` (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
);



DROP TABLE `keyword_annotations` IF EXISTS;
CREATE TABLE `keyword_annotations` (
  `id` bigint(20) NOT NULL,
  `keyword_id` bigint(20) NOT NULL,
  `rank_id` int(11) NOT NULL,
  `annotation_type` varchar(255) NOT NULL,
  `date` varchar(255) NOT NULL,
  `note` varchar(255) NOT NULL,
  `created_at` date NOT NULL,
  `updated_at` datetime DEFAULT NULL
);


DROP TABLE `keyword_daily_ranks` IF EXISTS;
CREATE TABLE `keyword_daily_ranks` (
  `id` bigint(20) NOT NULL,
  `keyword_id` bigint(20) NOT NULL,
  `google_rank` int(11) DEFAULT NULL,
  `google_base_rank` int(11) DEFAULT NULL,
  `google_display_rank` int(11) DEFAULT NULL,
  `google_movement` int(11) NOT NULL,
  `gurl_id` int(11) DEFAULT NULL,
  `yahoo_rank` int(11) DEFAULT NULL,
  `yahoo_movement` int(11) NOT NULL,
  `yurl_id` int(11) DEFAULT NULL,
  `bing_rank` int(11) DEFAULT NULL,
  `bing_movement` int(11) NOT NULL,
  `burl_id` int(11) DEFAULT NULL,
  `advertiser_competition` float DEFAULT NULL,
  `global_monthly_searches` int(11) DEFAULT NULL,
  `regional_monthly_searches` int(11) DEFAULT NULL,
  `cpc` decimal(5,2) DEFAULT NULL,
  `google_results` bigint(20) DEFAULT NULL,
  `google_kei` float DEFAULT NULL,
  `created_at` date NOT NULL,
  PRIMARY KEY (`id`)
);


DROP TABLE `keyword_google_local_ranks` IF EXISTS;
CREATE TABLE `keyword_google_local_ranks` (
  `id` bigint(20) NOT NULL,
  `keyword_id` bigint(20) NOT NULL,
  `rank` int(11) DEFAULT NULL,
  `display_rank` int(11) DEFAULT NULL,
  `base_rank` int(11) DEFAULT NULL,
  `movement` int(11) DEFAULT NULL,
  `url_id` int(11) DEFAULT NULL,
  `created_at` date NOT NULL,
  `updated_at` date DEFAULT NULL
);


DROP TABLE `keyword_google_ranks` IF EXISTS;
CREATE TABLE `keyword_google_ranks` (
  `id` bigint(20) NOT NULL,
  `keyword_id` bigint(20) NOT NULL,
  `rank` int(11) DEFAULT NULL,
  `display_rank` int(11) DEFAULT NULL,
  `base_rank` int(11) DEFAULT NULL,
  `movement` int(11) DEFAULT NULL,
  `url_id` int(11) DEFAULT NULL,
  `created_at` date NOT NULL,
  `updated_at` date NOT NULL
);



DROP TABLE `keyword_highest_ranks` IF EXISTS;
CREATE TABLE `keyword_highest_ranks` (
  `id` bigint(20) NOT NULL,
  `keyword_id` bigint(20) NOT NULL,
  `google_rank` int(11) DEFAULT NULL,
  `google_display_rank` int(11) DEFAULT NULL,
  `google_base_rank` int(11) DEFAULT NULL,
  `google_movement` int(11) NOT NULL,
  `gurl_id` int(11) DEFAULT NULL,
  `yahoo_rank` int(11) DEFAULT NULL,
  `yahoo_movement` int(11) NOT NULL,
  `yurl_id` int(11) DEFAULT NULL,
  `bing_rank` int(11) DEFAULT NULL,
  `bing_movement` int(11) NOT NULL,
  `burl_id` int(11) DEFAULT NULL,
  `created_at` date NOT NULL,
  `updated_at` date NOT NULL
);

DROP TABLE `keyword_stats` IF EXISTS;
CREATE TABLE `keyword_stats` (
  `id` bigint(20) NOT NULL,
  `keyword_id` bigint(20) NOT NULL,
  `google_results` bigint(20) DEFAULT NULL,
  `google_kei` float DEFAULT NULL,
  `yahoo_results` bigint(20) DEFAULT NULL,
  `yahoo_kei` float DEFAULT NULL,
  `bing_results` bigint(20) DEFAULT NULL,
  `bing_kei` float DEFAULT NULL,
  `created_at` date NOT NULL,
  `updated_at` date DEFAULT NULL
);

DROP TABLE `keywords` IF EXISTS;
CREATE TABLE `keywords` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `site_id` bigint(20) NOT NULL,
  `keyword` varchar(255) NOT NULL,
  `translation` varchar(255) DEFAULT NULL,
  `checksum` varchar(255) DEFAULT NULL,
  `tracking` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` date DEFAULT NULL,
  `updated_at` date DEFAULT NULL,
  `device_id` tinyint(4) NOT NULL DEFAULT '1',
  `location` varchar(255) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `market_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
);



DROP TABLE `keywords_search_volumes` IF EXISTS;
CREATE TABLE `keywords_search_volumes` (
  `keyword_id` bigint(20) NOT NULL,
  `search_volume_id` bigint(20) NOT NULL,
  PRIMARY KEY (`keyword_id`,`search_volume_id`)
);


DROP TABLE `keywords_tags` IF EXISTS;
CREATE TABLE `keywords_tags` (
  `keyword_id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL,
  `created_at` date NOT NULL,
  `updated_at` date NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`keyword_id`,`tag_id`)
);


DROP TABLE `markets` IF EXISTS;
CREATE TABLE `markets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `region` varchar(255) NOT NULL,
  `lang` varchar(255) NOT NULL,
  `region_name` varchar(255) NOT NULL,
  `lang_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);


DROP TABLE `persistent_logins` IF EXISTS;
CREATE TABLE `persistent_logins` (
  `login` varchar(255) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL,
  PRIMARY KEY (`series`)
);


DROP TABLE `report_attributes` IF EXISTS;
CREATE TABLE `report_attributes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `report_id` int(11) NOT NULL,
  `attribute_name` varchar(255) NOT NULL,
  `attribute_value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


DROP TABLE `reportable_objects` IF EXISTS;
CREATE TABLE `reportable_objects` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `scheduled_report_id` int(11) NOT NULL,
  `reportable_id` int(11) NOT NULL,
  `reportable_type` varchar(255) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
);



DROP TABLE `reports` IF EXISTS;
CREATE TABLE `reports` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(36) NOT NULL,
  `file_name` varchar(255) NOT NULL,
  `created_at` date NOT NULL,
  `updated_at` date NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `state` varchar(255) NOT NULL DEFAULT 'waiting',
  `progress` decimal(5,2) NOT NULL DEFAULT '0.00',
  `scheduled_report_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
);


DROP TABLE `routine_histories` IF EXISTS;
CREATE TABLE `routine_histories` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `crawl_date` date DEFAULT NULL,
  `routine` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE `scheduled_report_attributes` IF EXISTS;
CREATE TABLE `scheduled_report_attributes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `scheduled_report_id` bigint(20) NOT NULL,
  `attribute_name` varchar(255) NOT NULL,
  `attribute_value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


DROP TABLE `scheduled_reports` IF EXISTS;
CREATE TABLE `scheduled_reports` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `report_name` varchar(255) NOT NULL,
  `report_description` text,
  `email` varchar(255) DEFAULT NULL,
  `created_at` date NOT NULL,
  `updated_at` date NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `schedule_interval` varchar(32) NOT NULL DEFAULT 'never',
  `granularity` varchar(255) DEFAULT NULL,
  `report_type` varchar(255) NOT NULL DEFAULT 'RankingsReport',
  PRIMARY KEY (`id`)
);

DROP TABLE `scheduled_reports_sites` IF EXISTS;
CREATE TABLE `scheduled_reports_sites` (
  `site_id` bigint(20) NOT NULL,
  `scheduled_report_id` bigint(20) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`site_id`,`scheduled_report_id`)
);


DROP TABLE `search_volumes` IF EXISTS;
CREATE TABLE `search_volumes` (
  `id` bigint(20) NOT NULL,
  `advertiser_competition` float DEFAULT NULL,
  `global_monthly_searches` int(11) DEFAULT NULL,
  `regional_monthly_searches` int(11) DEFAULT NULL,
  `year_avg` text,
  `cpc` decimal(5,2) DEFAULT NULL,
  `created_at` date NOT NULL,
  `updated_at` date DEFAULT NULL,
  `latest_google_month` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


DROP TABLE `site_distribs` IF EXISTS;
CREATE TABLE `site_distribs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `site_id` bigint(20) NOT NULL,
  `crawl_date` int(8) NOT NULL,
  `one` int(11) DEFAULT NULL,
  `two` int(11) DEFAULT NULL,
  `three` int(11) DEFAULT NULL,
  `four` int(11) DEFAULT NULL,
  `five` int(11) DEFAULT NULL,
  `six_to_ten` int(11) DEFAULT NULL,
  `eleven_to_twenty` int(11) DEFAULT NULL,
  `twenty_one_to_thirty` int(11) DEFAULT NULL,
  `thirty_one_to_forty` int(11) DEFAULT NULL,
  `forty_one_to_fifty` int(11) DEFAULT NULL,
  `fifty_one_to_hundred` int(11) DEFAULT NULL,
  `non_ranking` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE `site_search_volumes` IF EXISTS;
CREATE TABLE `site_search_volumes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `site_id` bigint(20) NOT NULL,
  `crawl_date` int(8) NOT NULL,
  `global_aggregate_sv` bigint(20) DEFAULT NULL,
  `global_average_sv` decimal(15,4) DEFAULT NULL,
  `regional_aggregate_sv` bigint(20) DEFAULT NULL,
  `regional_average_sv` decimal(15,4) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);


DROP TABLE `site_stats` IF EXISTS;
CREATE TABLE `site_stats` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `site_id` bigint(20) NOT NULL,
  `crawl_date` int(8) NOT NULL,
  `backlinks` bigint(20) DEFAULT NULL,
  `indexed` bigint(20) DEFAULT NULL,
  `pagerank` int(11) DEFAULT '0',
  `non_ranking_value` int(11) DEFAULT NULL,
  `total_keywords` int(11) DEFAULT NULL,
  `tracked_keywords` int(11) DEFAULT NULL,
  `ranking_keywords` int(11) DEFAULT NULL,
  `untracked_keywords` int(11) DEFAULT NULL,
  `unique_keywords` int(11) DEFAULT NULL,
  `non_unique_keywords` int(11) DEFAULT NULL,
  `google_ranking_keywords` int(11) DEFAULT NULL,
  `google_base_ranking_keywords` int(11) DEFAULT NULL,
  `google_top_ten_keywords` int(11) DEFAULT NULL,
  `google_average` float DEFAULT NULL,
  `google_base_average` float DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);


DROP TABLE `sites` IF EXISTS;
CREATE TABLE `sites` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `title` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `created_at` date DEFAULT NULL,
  `updated_at` date DEFAULT NULL,
  `tracking` tinyint(1) NOT NULL DEFAULT '1',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `drop_www_prefix` tinyint(1) NOT NULL DEFAULT '1',
  `drop_directories` tinyint(1) NOT NULL DEFAULT '0',
  `contact_email` varchar(255) DEFAULT NULL,
  `treat_non_ranking_as` float DEFAULT NULL,
  `non_ranking_value` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


DROP TABLE `sites_alerts` IF EXISTS;
CREATE TABLE `sites_alerts` (
  `site_id` bigint(20) NOT NULL,
  `alert_id` bigint(20) NOT NULL,
  PRIMARY KEY (`site_id`,`alert_id`)
);


DROP TABLE `sites_annotations` IF EXISTS;
CREATE TABLE `sites_annotations` (
  `site_id` bigint(20) NOT NULL,
  `annotation_id` bigint(20) NOT NULL,
  PRIMARY KEY (`site_id`,`annotation_id`)
);


DROP TABLE `tag_distribs` IF EXISTS;
CREATE TABLE `tag_distribs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tag_id` bigint(20) NOT NULL,
  `crawl_date` int(8) NOT NULL,
  `one` int(11) DEFAULT NULL,
  `two` int(11) DEFAULT NULL,
  `three` int(11) DEFAULT NULL,
  `four` int(11) DEFAULT NULL,
  `five` int(11) DEFAULT NULL,
  `six_to_ten` int(11) DEFAULT NULL,
  `eleven_to_twenty` int(11) DEFAULT NULL,
  `twenty_one_to_thirty` int(11) DEFAULT NULL,
  `thirty_one_to_forty` int(11) DEFAULT NULL,
  `forty_one_to_fifty` int(11) DEFAULT NULL,
  `fifty_one_to_hundred` int(11) DEFAULT NULL,
  `non_ranking` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE `tag_search_volumes` IF EXISTS;
CREATE TABLE `tag_search_volumes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tag_id` bigint(20) NOT NULL,
  `crawl_date` int(8) NOT NULL,
  `global_aggregate_sv` bigint(20) DEFAULT NULL,
  `global_average_sv` decimal(15,4) DEFAULT NULL,
  `regional_aggregate_sv` bigint(20) DEFAULT NULL,
  `regional_average_sv` decimal(15,4) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE `tag_stats` IF EXISTS;
CREATE TABLE `tag_stats` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tag_id` bigint(20) NOT NULL,
  `crawl_date` int(8) NOT NULL,
  `total_keywords` int(11) DEFAULT NULL,
  `tracked_keywords` int(11) DEFAULT NULL,
  `ranking_keywords` int(11) DEFAULT NULL,
  `google_ranking_keywords` int(11) DEFAULT NULL,
  `google_base_ranking_keywords` int(11) DEFAULT NULL,
  `google_top_ten_keywords` int(11) DEFAULT NULL,
  `google_average` float DEFAULT NULL,
  `google_base_average` float DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);


DROP TABLE `tags` IF EXISTS;
CREATE TABLE `tags` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `site_id` bigint(20) NOT NULL,
  `tag` varchar(255) NOT NULL,
  `created_at` date DEFAULT NULL,
  `updated_at` date DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `report_tag` tinyint(1) NOT NULL DEFAULT '0',
  `filter_refresh` varchar(255) DEFAULT NULL,
  `backfill_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


DROP TABLE `url_infos` IF EXISTS;
CREATE TABLE `url_infos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url_id` bigint(20) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `malware` tinyint(1) DEFAULT NULL,
  `domain_name` varchar(255) DEFAULT NULL,
  `query_string` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


DROP TABLE `urls` IF EXISTS;
CREATE TABLE `urls` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


DROP TABLE `user_apis` IF EXISTS;
CREATE TABLE `user_apis` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `api_key` varchar(255) NOT NULL,
  `ips` varchar(255) DEFAULT NULL,
  `count` int(11) NOT NULL DEFAULT '0',
  `api_limit` int(11) NOT NULL DEFAULT '1000',
  `created_at` date DEFAULT NULL,
  `updated_at` date DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
);


DROP TABLE `users` IF EXISTS;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  `password` varchar(100) NOT NULL,
  `nicename` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);
