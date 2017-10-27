import React, { Component } from 'react';
import { connect } from 'react-redux'
import { submit } from 'redux-form'

import {
  Button
} from 'reactstrap';

const SiteSettingButton = ({ dispatch }) =>
<div>
  <Button onClick={() => dispatch(submit('siteSettingSubmit'))} type="submit" size="sm" color="primary"><i className="fa fa-dot-circle-o"></i> Submit</Button>
  <Button type="reset" size="sm" color="danger"><i className="fa fa-ban"></i> Reset</Button>
</div>


export default connect()(SiteSettingButton);
