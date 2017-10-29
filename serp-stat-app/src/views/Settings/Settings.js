import React, { Component } from 'react';
import {
  Row,
  Col,
  Card,
  CardHeader,
  CardFooter,
  CardBody
} from 'reactstrap';

import SiteSettingForm from "../Components/SiteSettingForm";
import SiteSettingButton from "../Components/SiteSettingButton";

class Settings extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    return (
      <div className="animated fadeIn">
        <Row>
          <Col>
            <Card>
              <CardHeader>
                <strong>Site Setting</strong>
              </CardHeader>
              <CardBody>
                <SiteSettingForm {...this.props}/>
              </CardBody>
              <CardFooter>
                <SiteSettingButton {...this.props}/>
              </CardFooter>
            </Card>
          </Col>
        </Row>
      </div>
    )
  }
}

export default Settings;
