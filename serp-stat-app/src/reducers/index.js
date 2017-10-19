import { combineReducers } from "redux"

import sites from "./sitesReducer"
import user from "./userReducer"

export default combineReducers({
  sites,
  user,
})
