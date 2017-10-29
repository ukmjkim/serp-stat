import { combineReducers } from 'redux';
import { reducer as formReducer } from 'redux-form';

import UserReducers from './user';
import SitesReducers from './sites';
import TagsReducers from './tags';
import KeywordReducers from './keywords';

export default combineReducers({
  user: UserReducers,
  sites: SitesReducers,
  tags: TagsReducers,
  keywords: KeywordReducers,
  form: formReducer
})
