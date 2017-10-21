import { combineReducers } from "redux"

import { sites, sitesHasErrored, sitesIsLoading, sitesSelected } from "./sites"
import { tags, tagsHasErrored, tagsHasFetched, tagsIsLoading } from "./tags"
import { user, userHasErrored, userIsLoading } from "./user"

export default combineReducers({
  sites,
  sitesHasErrored,
  sitesIsLoading,
  sitesSelected,
  tags,
  tagsHasErrored,
  tagsHasFetched,
  tagsIsLoading,
  user,
  userHasErrored,
  userIsLoading
})
