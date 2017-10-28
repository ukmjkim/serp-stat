import { combineReducers } from 'redux';
import { reducer as formReducer } from 'redux-form';

import SitesReducers from './sites';
import TagsReducers from './tags';
import UserReducers from './user';

export default combineReducers({
  sites: SitesReducers,
  tags: TagsReducers,
  user: UserReducers,
  form: formReducer
})
