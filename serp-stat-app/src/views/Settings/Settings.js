import React, { Component } from 'react';
import { Field, reduxForm } from 'redux-form';
import {
  Row,
  Col,
  Button,
  Card,
  CardHeader,
  CardFooter,
  CardBody,
  Form,
  FormGroup,
  FormText,
  Label,
  Input,
  InputGroup
} from 'reactstrap';

const FIELDS = new Map([
  ["title", {
    type: 'input',
    label: 'Title for Site',
    validate: () => (
      console.log("validate")
    )
  }],
  ["url", {
    type: 'input',
    label: 'Url for Site',
    validate: () => (
      console.log("validate")
    )
  }],
  ["non_ranking_value", {
    type: 'input',
    label: 'Non Ranking Value',
    validate: () => (
      console.log("validate")
    )
  }],
  ["tracking", {
    type: 'checkbox',
    label: 'Tracking',
    validate: () => (
      console.log("validate")
    )
  }],
  ["treat_non_ranking_as", {
    type: 'checkbox',
    label: 'Non Ranking Null',
    validate: () => (
      console.log("validate")
    )
  }],
  ["drop_www_prefix", {
    type: 'checkbox',
    label: 'Drop WWW',
    validate: () => (
      console.log("validate")
    )
  }],
  ["drop_directories", {
    type: 'checkbox',
    label: 'Drop Directories',
    validate: () => (
      console.log("validate")
    )
  }],
]);

const renderInput = field =>
  <div className="controls">
    <InputGroup>
      <Input {...field.input} type={field.type}/>
    </InputGroup>
    {field.meta.touched &&
     field.meta.error &&
     <FormText className="help-block">{field.meta.error}</FormText>}
  </div>

class Settings extends Component {
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

    // field list
    const fieldList = (fields) => {
      return Array.from( fields ).map( (val, key) => this.renderField(val, key) )
    };

    return (
      <div className="animated fadeIn">
        <Row>
          <Col>
            <Card>
              <CardHeader>
                <strong>Site Setting</strong>
              </CardHeader>
              <CardBody>
                <Form onSubmit={handleSubmit(props => this.onSubmit(props))} action="" method="post" encType="multipart/form-data" className="form-horizontal">
                {fieldList(FIELDS)}
                </Form>
              </CardBody>
              <CardFooter>
                <Button type="submit" size="sm" color="primary"><i className="fa fa-dot-circle-o"></i> Submit</Button>
                <Button type="reset" size="sm" color="danger"><i className="fa fa-ban"></i> Reset</Button>
              </CardFooter>
            </Card>
          </Col>
        </Row>
      </div>
    )
  }
}

function validate(values) {
  const errors = {};

  for (let [key, value] of FIELDS) {
    if (!values[key]) {
      errors[key] = `Enter a ${key}`;
    }
  }

  return errors;
}

export default reduxForm({
  form: 'settings',
  fields: Array.from( FIELDS ).map( (val, key) => val[0] ),
  validate
})(Settings);
