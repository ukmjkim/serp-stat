import React, { Component } from 'react';
import {
    Badge,
    Row,
    Col,
    Card,
    CardHeader,
    CardBody,
    CardFooter
  } from 'reactstrap';

import KeywordTable from '../Components/KeywordTable';
import KeywordTableHead from '../Components/KeywordTableHead';
import KeywordChart from '../Components/KeywordChart';
import KeywordChartFooter from '../Components/KeywordChartFooter';

export default class Keyword extends Component {
    constructor(props) {
      super(props);
    }

    render() {

      return (
        <div className="animated fadeIn">
          <Row>
          <Col>
          <Card>
              <CardHeader>
                <KeywordTableHead {...this.props}/>
              </CardHeader>
              <CardBody>
                <KeywordTable {...this.props}/>
              </CardBody>
          </Card>
          </Col>
          </Row>
          <Row>
            <Col>
              <Card>
                <CardBody>
                  <KeywordChart />
                </CardBody>
                <CardFooter>
                  <KeywordChartFooter />
                </CardFooter>
              </Card>
            </Col>
          </Row>
        </div>
      )
    }
}
