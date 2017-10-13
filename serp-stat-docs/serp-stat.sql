USE serpstat;

-- MySQL dump 10.13  Distrib 5.7.18, for osx10.12 (x86_64)
--
-- Host: localhost    Database: searchstat
-- ------------------------------------------------------
-- Server version	5.7.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `alerts`
--

DROP TABLE IF EXISTS `alerts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alerts` (
  `id` bigint(20) NOT NULL,
  `alert` varchar(255) NOT NULL,
  `created_at` date NOT NULL,
  `updated_at` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `annotations`
--

DROP TABLE IF EXISTS `annotations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  PRIMARY KEY (`id`),
  KEY `index_annotations_on_annotateable_type_and_annotateable_id` (`annotateable_type`,`annotateable_id`),
  KEY `site_id_date` (`id`,`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `backfills`
--

DROP TABLE IF EXISTS `backfills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  PRIMARY KEY (`id`),
  KEY `index_backfills_start_time` (`backfillable_type`,`backfillable_id`,`start_time`),
  KEY `from_date_to_date` (`from_date`,`to_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bad_parses`
--

DROP TABLE IF EXISTS `bad_parses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bad_parses` (
  `keyword_id` int(11) NOT NULL,
  `created_at` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bulk_jobs`
--

DROP TABLE IF EXISTS `bulk_jobs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  PRIMARY KEY (`id`),
  KEY `index_bulk_jobs_on_api` (`api`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `calendar`
--

DROP TABLE IF EXISTS `calendar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `calendar` (
  `c_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `crawl_keywords`
--

DROP TABLE IF EXISTS `crawl_keywords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crawl_keywords` (
  `keyword_id` int(11) NOT NULL,
  `created_at` date NOT NULL,
  UNIQUE KEY `keyword_date` (`created_at`,`keyword_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `crawl_states`
--

DROP TABLE IF EXISTS `crawl_states`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `engine_crawl_date_uniq` (`engine`,`crawl_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `devices`
--

DROP TABLE IF EXISTS `devices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `devices` (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `keyword_annotations`
--

DROP TABLE IF EXISTS `keyword_annotations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `keyword_annotations` (
  `id` bigint(20) NOT NULL,
  `keyword_id` bigint(20) NOT NULL,
  `rank_id` int(11) NOT NULL,
  `annotation_type` varchar(255) NOT NULL,
  `date` varchar(255) NOT NULL,
  `note` varchar(255) NOT NULL,
  `created_at` date NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  KEY `user_site_type_date` (`keyword_id`,`annotation_type`,`date`),
  CONSTRAINT `keyword_annotations` FOREIGN KEY (`keyword_id`) REFERENCES `keywords` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `keyword_daily_ranks`
--

DROP TABLE IF EXISTS `keyword_daily_ranks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  PRIMARY KEY (`id`),
  KEY `keyword_date_uniq` (`keyword_id`,`created_at`),
  KEY `keyword_id` (`keyword_id`),
  CONSTRAINT `daily_ranks` FOREIGN KEY (`keyword_id`) REFERENCES `keywords` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `keyword_google_local_ranks`
--

DROP TABLE IF EXISTS `keyword_google_local_ranks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `keyword_google_local_ranks` (
  `id` bigint(20) NOT NULL,
  `keyword_id` bigint(20) NOT NULL,
  `rank` int(11) DEFAULT NULL,
  `display_rank` int(11) DEFAULT NULL,
  `base_rank` int(11) DEFAULT NULL,
  `movement` int(11) DEFAULT NULL,
  `url_id` int(11) DEFAULT NULL,
  `created_at` date NOT NULL,
  `updated_at` date DEFAULT NULL,
  UNIQUE KEY `date_keyword_rank_uniq` (`created_at`,`keyword_id`,`rank`),
  KEY `kw_search` (`keyword_id`,`created_at`),
  KEY `kw_search_move` (`keyword_id`,`movement`,`created_at`),
  KEY `kw_search_rank` (`keyword_id`,`rank`,`created_at`),
  CONSTRAINT `google_local_ranks` FOREIGN KEY (`keyword_id`) REFERENCES `keywords` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `keyword_google_ranks`
--

DROP TABLE IF EXISTS `keyword_google_ranks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `keyword_google_ranks` (
  `id` bigint(20) NOT NULL,
  `keyword_id` bigint(20) NOT NULL,
  `rank` int(11) DEFAULT NULL,
  `display_rank` int(11) DEFAULT NULL,
  `base_rank` int(11) DEFAULT NULL,
  `movement` int(11) DEFAULT NULL,
  `url_id` int(11) DEFAULT NULL,
  `created_at` date NOT NULL,
  `updated_at` date NOT NULL,
  UNIQUE KEY `date_keyword_rank_uniq` (`created_at`,`keyword_id`,`rank`),
  KEY `kw_search` (`keyword_id`,`created_at`),
  KEY `kw_search_move` (`keyword_id`,`movement`,`created_at`),
  KEY `kw_search_rank` (`keyword_id`,`rank`,`created_at`),
  CONSTRAINT `google_ranks` FOREIGN KEY (`keyword_id`) REFERENCES `keywords` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `keyword_highest_ranks`
--

DROP TABLE IF EXISTS `keyword_highest_ranks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  `updated_at` date NOT NULL,
  UNIQUE KEY `keyword_date_uniq` (`keyword_id`,`created_at`),
  KEY `keyword_rank_created` (`keyword_id`,`google_rank`,`google_base_rank`,`yahoo_rank`,`bing_rank`,`created_at`),
  CONSTRAINT `highest_ranks` FOREIGN KEY (`keyword_id`) REFERENCES `keywords` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `keyword_stats`
--

DROP TABLE IF EXISTS `keyword_stats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  `updated_at` date DEFAULT NULL,
  UNIQUE KEY `keyword_date_uniq` (`keyword_id`,`created_at`),
  KEY `keyword_id_date` (`keyword_id`,`created_at`),
  CONSTRAINT `keyword_stats` FOREIGN KEY (`keyword_id`) REFERENCES `keywords` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `keywords`
--

DROP TABLE IF EXISTS `keywords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_site_keyword_market_location_device` (`site_id`,`keyword`,`location`,`device_id`),
  KEY `keyword_market_location_device` (`keyword`,`location`,`device_id`),
  KEY `site_location` (`site_id`,`location`),
  KEY `tracking_site_id` (`tracking`,`site_id`),
  KEY `device_id` (`device_id`),
  KEY `keywords_market` (`market_id`),
  CONSTRAINT `keywords` FOREIGN KEY (`site_id`) REFERENCES `sites` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `keywords_ibfk_1` FOREIGN KEY (`device_id`) REFERENCES `devices` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `keywords_market` FOREIGN KEY (`market_id`) REFERENCES `markets` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `keywords_search_volumes`
--

DROP TABLE IF EXISTS `keywords_search_volumes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `keywords_search_volumes` (
  `keyword_id` bigint(20) NOT NULL,
  `search_volume_id` bigint(20) NOT NULL,
  PRIMARY KEY (`keyword_id`,`search_volume_id`),
  KEY `FK_SEARCH_VOLUME` (`search_volume_id`),
  CONSTRAINT `FK_KEYWORD` FOREIGN KEY (`keyword_id`) REFERENCES `keywords` (`id`),
  CONSTRAINT `FK_SEARCH_VOLUME` FOREIGN KEY (`search_volume_id`) REFERENCES `search_volumes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `keywords_tags`
--

DROP TABLE IF EXISTS `keywords_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `keywords_tags` (
  `keyword_id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL,
  `created_at` date NOT NULL,
  `updated_at` date NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`keyword_id`,`tag_id`),
  KEY `FK_TAG` (`tag_id`),
  CONSTRAINT `FK_KEYWORD1` FOREIGN KEY (`keyword_id`) REFERENCES `keywords` (`id`),
  CONSTRAINT `FK_TAG` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `markets`
--

DROP TABLE IF EXISTS `markets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `markets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `region` varchar(255) NOT NULL,
  `lang` varchar(255) NOT NULL,
  `region_name` varchar(255) NOT NULL,
  `lang_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `persistent_logins`
--

DROP TABLE IF EXISTS `persistent_logins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persistent_logins` (
  `login` varchar(255) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `report_attributes`
--

DROP TABLE IF EXISTS `report_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report_attributes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `report_id` int(11) NOT NULL,
  `attribute_name` varchar(255) NOT NULL,
  `attribute_value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `report_attributes_name` (`report_id`,`attribute_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reportable_objects`
--

DROP TABLE IF EXISTS `reportable_objects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reportable_objects` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `scheduled_report_id` int(11) NOT NULL,
  `reportable_id` int(11) NOT NULL,
  `reportable_type` varchar(255) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reports`
--

DROP TABLE IF EXISTS `reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `routine_histories`
--

DROP TABLE IF EXISTS `routine_histories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `routine_histories` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `crawl_date` date DEFAULT NULL,
  `routine` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_routine_histories_on_crawl_date_and_routine` (`crawl_date`,`routine`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scheduled_report_attributes`
--

DROP TABLE IF EXISTS `scheduled_report_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scheduled_report_attributes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `scheduled_report_id` bigint(20) NOT NULL,
  `attribute_name` varchar(255) NOT NULL,
  `attribute_value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `scheduled_report_attributes` (`scheduled_report_id`),
  CONSTRAINT `scheduled_report_attributes` FOREIGN KEY (`scheduled_report_id`) REFERENCES `scheduled_reports` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scheduled_reports`
--

DROP TABLE IF EXISTS `scheduled_reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scheduled_reports_sites`
--

DROP TABLE IF EXISTS `scheduled_reports_sites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scheduled_reports_sites` (
  `site_id` bigint(20) NOT NULL,
  `scheduled_report_id` bigint(20) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`site_id`,`scheduled_report_id`),
  KEY `FK_SCHEDULED_REPORT` (`scheduled_report_id`),
  CONSTRAINT `FK_SCHEDULED_REPORT` FOREIGN KEY (`scheduled_report_id`) REFERENCES `scheduled_reports` (`id`),
  CONSTRAINT `FK_SITE` FOREIGN KEY (`site_id`) REFERENCES `sites` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `search_volumes`
--

DROP TABLE IF EXISTS `search_volumes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  PRIMARY KEY (`id`),
  KEY `keyword_id_date` (`created_at`),
  KEY `kwid_gsv_created` (`global_monthly_searches`,`created_at`),
  KEY `kwid_gsv_rsv_created` (`global_monthly_searches`,`regional_monthly_searches`,`created_at`),
  KEY `kwid_rsv_created` (`regional_monthly_searches`,`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `site_aggregate_search_volumes`
--

DROP TABLE IF EXISTS `site_aggregate_search_volumes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `site_aggregate_search_volumes` (
  `id` bigint(20) NOT NULL,
  `site_id` bigint(20) NOT NULL,
  `global_aggregate_sv` bigint(20) NOT NULL,
  `global_average_sv` decimal(15,4) NOT NULL,
  `regional_aggregate_sv` bigint(20) NOT NULL,
  `regional_average_sv` decimal(15,4) NOT NULL,
  `untagged_global_aggregate_sv` bigint(20) NOT NULL,
  `untagged_global_average_sv` decimal(15,4) NOT NULL,
  `untagged_regional_aggregate_sv` bigint(20) NOT NULL,
  `untagged_regional_average_sv` decimal(15,4) NOT NULL,
  `crawl_date` date NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  UNIQUE KEY `site_crawl_date_unique` (`site_id`,`crawl_date`),
  CONSTRAINT `site_aggregate_search_volumes` FOREIGN KEY (`site_id`) REFERENCES `sites` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `site_distribs`
--

DROP TABLE IF EXISTS `site_distribs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `site_distribs` (
  `id` bigint(20) NOT NULL,
  `site_id` bigint(20) NOT NULL,
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
  `created_at` date NOT NULL,
  KEY `google_site_distribs_fk_id_created_at` (`site_id`,`created_at`),
  CONSTRAINT `google_site_distribs` FOREIGN KEY (`site_id`) REFERENCES `sites` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `site_stats`
--

DROP TABLE IF EXISTS `site_stats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `site_stats` (
  `id` bigint(20) NOT NULL,
  `site_id` bigint(20) NOT NULL,
  `backlinks` bigint(20) DEFAULT NULL,
  `indexed` bigint(20) DEFAULT NULL,
  `pagerank` int(11) DEFAULT '0',
  `total_keywords` int(11) DEFAULT NULL,
  `ranking_keywords` int(11) DEFAULT NULL,
  `google_top_ten` int(11) DEFAULT NULL,
  `yahoo_top_ten` int(11) DEFAULT NULL,
  `bing_top_ten` int(11) DEFAULT NULL,
  `created_at` date NOT NULL,
  `tracked_keywords` int(11) DEFAULT NULL,
  `untracked_keywords` int(11) DEFAULT NULL,
  `unique_keywords` int(11) DEFAULT NULL,
  `non_unique_keywords` int(11) DEFAULT NULL,
  `google_average` float DEFAULT NULL,
  `yahoo_average` float DEFAULT NULL,
  `bing_average` float DEFAULT NULL,
  `google_base_average` float DEFAULT NULL,
  `google_ranking_keywords` int(11) DEFAULT NULL,
  `yahoo_ranking_keywords` int(11) DEFAULT NULL,
  `bing_ranking_keywords` int(11) DEFAULT NULL,
  `google_base_ranking_keywords` int(11) DEFAULT NULL,
  `untagged_google_average` float DEFAULT NULL,
  `untagged_yahoo_average` float DEFAULT NULL,
  `untagged_bing_average` float DEFAULT NULL,
  `untagged_google_base_average` float DEFAULT NULL,
  `untagged_ranking_keywords` int(11) DEFAULT NULL,
  `untagged_tracked_keywords` int(11) DEFAULT NULL,
  `untagged_total_keywords` int(11) DEFAULT NULL,
  `untagged_google_top_ten` int(11) DEFAULT NULL,
  `untagged_yahoo_top_ten` int(11) DEFAULT NULL,
  `untagged_bing_top_ten` int(11) DEFAULT NULL,
  `non_ranking_value` int(11) DEFAULT NULL,
  KEY `site_id_created` (`site_id`,`created_at`),
  CONSTRAINT `site_stats` FOREIGN KEY (`site_id`) REFERENCES `sites` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sites`
--

DROP TABLE IF EXISTS `sites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sites` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `title` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `created_at` date NOT NULL,
  `updated_at` date NOT NULL,
  `tracking` tinyint(1) NOT NULL DEFAULT '1',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `drop_www_prefix` tinyint(1) NOT NULL DEFAULT '1',
  `drop_directories` tinyint(1) NOT NULL DEFAULT '0',
  `contact_email` varchar(255) DEFAULT NULL,
  `treat_non_ranking_as` float DEFAULT NULL,
  `non_ranking_value` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `group_id` (`user_id`),
  KEY `index_sites_on_title` (`title`),
  CONSTRAINT `sites` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sites_alerts`
--

DROP TABLE IF EXISTS `sites_alerts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sites_alerts` (
  `site_id` bigint(20) NOT NULL,
  `alert_id` bigint(20) NOT NULL,
  PRIMARY KEY (`site_id`,`alert_id`),
  KEY `sites_alerts_alert` (`alert_id`),
  CONSTRAINT `sites_alerts_alert` FOREIGN KEY (`alert_id`) REFERENCES `alerts` (`id`),
  CONSTRAINT `sites_alerts_site` FOREIGN KEY (`site_id`) REFERENCES `sites` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sites_annotations`
--

DROP TABLE IF EXISTS `sites_annotations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sites_annotations` (
  `site_id` bigint(20) NOT NULL,
  `annotation_id` bigint(20) NOT NULL,
  PRIMARY KEY (`site_id`,`annotation_id`),
  KEY `sites_annotations_annotation` (`annotation_id`),
  CONSTRAINT `sites_annotations_annotation` FOREIGN KEY (`annotation_id`) REFERENCES `annotations` (`id`),
  CONSTRAINT `sites_annotations_site` FOREIGN KEY (`site_id`) REFERENCES `sites` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tag_aggregate_search_volumes`
--

DROP TABLE IF EXISTS `tag_aggregate_search_volumes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag_aggregate_search_volumes` (
  `id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL,
  `global_aggregate_sv` bigint(20) NOT NULL,
  `global_average_sv` decimal(15,4) NOT NULL,
  `regional_aggregate_sv` bigint(20) NOT NULL,
  `regional_average_sv` decimal(15,4) NOT NULL,
  `crawl_date` date NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  UNIQUE KEY `tag_crawl_date_unique` (`tag_id`,`crawl_date`),
  CONSTRAINT `tag_aggregate_search_volumes` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tag_annotations`
--

DROP TABLE IF EXISTS `tag_annotations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag_annotations` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tag_id` bigint(20) NOT NULL,
  `annotation_type` varchar(255) NOT NULL,
  `date` varchar(255) NOT NULL,
  `note` varchar(255) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `user_tag_type_date` (`tag_id`,`annotation_type`,`date`),
  CONSTRAINT `tag_annotations` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tag_distribs`
--

DROP TABLE IF EXISTS `tag_distribs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag_distribs` (
  `id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL,
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
  `created_at` date NOT NULL,
  KEY `google_tag_distribs_fk_id_created_at` (`tag_id`,`created_at`),
  CONSTRAINT `google_tag_distribs` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tag_sovs`
--

DROP TABLE IF EXISTS `tag_sovs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag_sovs` (
  `id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL,
  `domain` varchar(255) NOT NULL,
  `sov` float NOT NULL,
  `crawl_date` date NOT NULL,
  `weighted_score` float NOT NULL,
  `is_valid` tinyint(1) NOT NULL DEFAULT '1',
  UNIQUE KEY `index_tag_sovs_on_crawl_date_and_domain_and_tag_id` (`crawl_date`,`domain`,`tag_id`),
  KEY `sov_crawl_date` (`crawl_date`),
  KEY `sov_tag_id_crawl_date` (`tag_id`,`crawl_date`),
  KEY `sov_tag_id_domain_value` (`tag_id`,`domain`,`sov`),
  KEY `sov_tag_id_value` (`tag_id`,`sov`),
  CONSTRAINT `tag_sovs` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tag_stats`
--

DROP TABLE IF EXISTS `tag_stats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag_stats` (
  `id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL,
  `tracked_keywords` int(11) DEFAULT NULL,
  `google_average` float DEFAULT NULL,
  `yahoo_average` float DEFAULT NULL,
  `bing_average` float DEFAULT NULL,
  `total_keywords` int(11) DEFAULT NULL,
  `google_top_ten` int(11) DEFAULT NULL,
  `yahoo_top_ten` int(11) DEFAULT NULL,
  `bing_top_ten` int(11) DEFAULT NULL,
  `google_ranking_keywords` int(11) DEFAULT NULL,
  `yahoo_ranking_keywords` int(11) DEFAULT NULL,
  `bing_ranking_keywords` int(11) DEFAULT NULL,
  `created_at` date NOT NULL,
  `google_base_average` float DEFAULT NULL,
  `google_base_ranking_keywords` int(11) DEFAULT NULL,
  `ranking_keywords` int(11) DEFAULT NULL,
  KEY `tag_id_created` (`tag_id`,`created_at`),
  CONSTRAINT `tag_stats` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tags`
--

DROP TABLE IF EXISTS `tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tags` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `site_id` bigint(20) NOT NULL,
  `tag` varchar(255) NOT NULL,
  `created_at` date NOT NULL,
  `updated_at` date NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `report_tag` tinyint(1) NOT NULL DEFAULT '0',
  `filter_refresh` varchar(255) DEFAULT NULL,
  `backfill_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_backfill_id_for_tags_tb` (`backfill_id`),
  KEY `tags_site_id_tag` (`site_id`,`tag`),
  CONSTRAINT `tags` FOREIGN KEY (`site_id`) REFERENCES `sites` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `url_infos`
--

DROP TABLE IF EXISTS `url_infos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `url_infos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url_id` bigint(20) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `malware` tinyint(1) DEFAULT NULL,
  `domain_name` varchar(255) DEFAULT NULL,
  `query_string` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `url_infos_url` (`url_id`),
  CONSTRAINT `url_infos_url` FOREIGN KEY (`url_id`) REFERENCES `urls` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `urls`
--

DROP TABLE IF EXISTS `urls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `urls` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_urls_on_value` (`value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_apis`
--

DROP TABLE IF EXISTS `user_apis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_apis` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `key` varchar(255) NOT NULL,
  `ips` varchar(255) DEFAULT NULL,
  `count` int(11) NOT NULL DEFAULT '0',
  `limit` int(11) NOT NULL DEFAULT '1000',
  `created_at` date NOT NULL,
  `updated_at` date NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `apis_user` (`user_id`),
  CONSTRAINT `apis_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(255) DEFAULT NULL,
  `nicename` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-12 17:37:36
