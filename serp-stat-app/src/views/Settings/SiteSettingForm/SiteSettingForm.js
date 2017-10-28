import React, { Component } from 'react';
import { connect } from 'react-redux'
import { Field, reduxForm, SubmissionError } from 'redux-form';
import {
  Col,
  Form,
  FormGroup,
  FormText,
  Label,
  Input,
  InputGroup
} from 'reactstrap';

import { updateSite } from "../../../actions/sites"

import { FIELDS } from './_fields';

const renderInput = field =>
  <div className="controls">
    <InputGroup>
      <Input {...field.input} type={field.type}/>
    </InputGroup>
    {field.meta.touched &&
     field.meta.error &&
     <FormText className="help-block">{field.meta.error}</FormText>}
  </div>

function submit(values, dispatch) {
  console.log("======================================= SiteSettingForm");
console.log(values);
  dispatch(updateSite(values.id, values, sessionStorage.getItem('jwtToken')));
}

class SiteSettingForm extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  onSubmit(props) {
    alert('Settings Submitted');
  }

  renderField(fieldConfig, field) {
    const fieldHelper = this.props.fields[field];
    const fieldKey = fieldConfig[0];
    const fieldValue = fieldConfig[1];

    if (fieldValue.type == "checkbox") {
      return (
        <FormGroup key={fieldKey} row>
          <Col md="3"><Label htmlFor="appendedInput">{fieldValue.label}</Label></Col>
          <Col md="9">
            <FormGroup check>
              <div className="checkbox">
                <Field name={fieldKey} component={renderInput} type={fieldValue.type} />
              </div>
            </FormGroup>
          </Col>
        </FormGroup>
      )
    } else {
      return (
        <FormGroup key={fieldKey}>
          <Label htmlFor="appendedInput">{fieldValue.label}</Label>
          <Field name={fieldKey} component={renderInput} type={fieldValue.type} />
        </FormGroup>
      )
    }
  }

  render() {
    const { handleSubmit } = this.props;

    const fieldList = (fields) => {
      return Array.from( fields ).map( (val, key) => this.renderField(val, key) )
    };

    return (
      <Form onSubmit={handleSubmit} action="" method="post" encType="multipart/form-data" className="form-horizontal">
      {fieldList(FIELDS)}
      </Form>
    )
  }
}

function validate(values) {
  const errors = {};

  for (let [key, value] of FIELDS) {
    let { response, message } = value.validate(key, values[key]);
    if (!response) {
      errors[key] = message;
    }
  }

  return errors;
}

const mapStateToProps = (state) => {
  return {
    activeSite: state.sites.activeSite
  }
};

const mapDispatchToProps = (dispatch) => {
  return {
    updateSite: (site) => dispatch(updateSite(site))
  }
};
SiteSettingForm = connect(mapStateToProps, mapDispatchToProps)(SiteSettingForm);

export default reduxForm({
  form: 'siteSettingSubmit',
  fields: Array.from( FIELDS ).map( (val, key) => val[0] ),
  validate,
  onSubmit: submit
})(SiteSettingForm);
