import React, { Component } from 'react';
import {Bar, Line} from 'react-chartjs-2';
import {
  Row,
  Col,
  Progress,
  Card,
  CardHeader,
  CardBody,
  Table
} from 'reactstrap';

const brandPrimary = '#20a8d8';
const brandSuccess = '#4dbd74';
const brandInfo = '#63c2de';
const brandWarning = '#f8cb00';
const brandDanger = '#f86c6b';


// sparkline charts
const sparkLineChartData = [
  {
    data: [35, 23, 56, 22, 97, 23, 64],
    label: 'New Clients'
  },
  {
    data: [65, 59, 84, 84, 51, 55, 40],
    label: 'Recurring Clients'
  },
  {
    data: [35, 23, 56, 22, 97, 23, 64],
    label: 'Pageviews'
  },
  {
    data: [65, 59, 84, 84, 51, 55, 40],
    label: 'Organic'
  },
  {
    data: [78, 81, 80, 45, 34, 12, 40],
    label: 'CTR'
  },
  {
    data: [1, 13, 9, 17, 34, 41, 38],
    label: 'Bounce Rate'
  }
];

const makeSparkLineData = (dataSetNo, variant) => {
  const dataset = sparkLineChartData[dataSetNo];
  const data = {
    labels: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'],
    datasets: [
      {
        backgroundColor: 'transparent',
        borderColor: variant ? variant : '#c2cfd6',
        data: dataset.data,
        label: dataset.label
      }
    ],
  };
  return () => data;
};

const sparklineChartOpts = {
  responsive: true,
  maintainAspectRatio: true,
  scales: {
    xAxes: [{
      display: false,
    }],
    yAxes: [{
      display: false,
    }]
  },
  elements: {
    line: {
      borderWidth: 2
    },
    point: {
      radius: 0,
      hitRadius: 10,
      hoverRadius: 4,
      hoverBorderWidth: 3,
    }
  },
  legend: {
    display: false
  }
};

export default class Dashboard extends Component {
  render() {

    return (
      <div className="animated fadeIn">
        <Row>
          <Col>
            <Card>
              <CardHeader>
                Sites Performance Overview
              </CardHeader>
              <CardBody>
                <Table hover responsive className="table-outline mb-0 d-none d-sm-table">
                  <thead className="thead-default">
                  <tr>
                    <th className="text-center">Site</th>
                    <th className="text-center">Keywords Trend</th>
                    <th className="text-center">Performance Trend</th>
                    <th className="text-center"># of Tags</th>
                    <th className="text-center">Performed</th>
                    <th className="text-center">Need Efforts</th>
                  </tr>
                  </thead>
                  <tbody>
                  <tr>
                    <td className="text-center">
                      <div>lululemon.ca</div>
                      <div className="small text-muted">
                        Created: Jan 1, 2015
                      </div>
                    </td>
                    <td>
                      <div className="callout callout-info">
                        <small className="text-muted">New Clients</small>
                        <br/>
                        <strong className="h4">9,123</strong>
                        <div className="chart-wrapper">
                          <Line data={makeSparkLineData(0, brandPrimary)} options={sparklineChartOpts} width={100} height={30}/>
                        </div>
                      </div>
                    </td>
                    <td>
                      <div className="clearfix">
                        <div className="callout callout-danger">
                          <small className="text-muted">Recurring Clients</small>
                          <br/>
                          <strong className="h4">2.5</strong>
                          <div className="chart-wrapper">
                            <Line data={makeSparkLineData(1, brandDanger)} options={sparklineChartOpts} width={100} height={30}/>
                          </div>
                        </div>
                      </div>
                    </td>
                    <td className="text-center">
                      <div>15</div>
                      <div className="small text-muted">
                        Last Modified: Jan 1, 2015
                      </div>
                    </td>
                    <td className="text-center">
                      <div>3.5</div>
                      <div className="small text-muted">
                        Seasonal Target
                      </div>
                    </td>
                    <td className="text-center">
                      <div>22.5</div>
                      <div className="small text-muted">
                        New Business
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td className="text-center">
                      <div>lululemon.uk</div>
                      <div className="small text-muted">
                        Created: Jan 1, 2015
                      </div>
                    </td>
                    <td>
                      <div className="callout callout-info">
                        <small className="text-muted">New Clients</small>
                        <br/>
                        <strong className="h4">8,153</strong>
                        <div className="chart-wrapper">
                          <Line data={makeSparkLineData(2, brandPrimary)} options={sparklineChartOpts} width={100} height={30}/>
                        </div>
                      </div>
                    </td>
                    <td>
                      <div className="clearfix">
                        <div className="callout callout-danger">
                          <small className="text-muted">Recurring Clients</small>
                          <br/>
                          <strong className="h4">5.6</strong>
                          <div className="chart-wrapper">
                            <Line data={makeSparkLineData(3, brandDanger)} options={sparklineChartOpts} width={100} height={30}/>
                          </div>
                        </div>
                      </div>
                    </td>
                    <td className="text-center">
                      <div>121</div>
                      <div className="small text-muted">
                        Last Modified: Jan 1, 2015
                      </div>
                    </td>
                    <td className="text-center">
                      <div>2.7</div>
                      <div className="small text-muted">
                        Top Revenue
                      </div>
                    </td>
                    <td className="text-center">
                      <div>13.6</div>
                      <div className="small text-muted">
                        Red Ocean
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td className="text-center">
                      <div>lululemon.au</div>
                      <div className="small text-muted">
                        Created: Jan 1, 2015
                      </div>
                    </td>
                    <td>
                      <div className="callout callout-info">
                        <small className="text-muted">New Clients</small>
                        <br/>
                        <strong className="h4">2,123</strong>
                        <div className="chart-wrapper">
                          <Line data={makeSparkLineData(4, brandPrimary)} options={sparklineChartOpts} width={100} height={30}/>
                        </div>
                      </div>
                    </td>
                    <td>
                      <div className="clearfix">
                        <div className="callout callout-danger">
                          <small className="text-muted">Recurring Clients</small>
                          <br/>
                          <strong className="h4">1.5</strong>
                          <div className="chart-wrapper">
                            <Line data={makeSparkLineData(5, brandDanger)} options={sparklineChartOpts} width={100} height={30}/>
                          </div>
                        </div>
                      </div>
                    </td>
                    <td className="text-center">
                      <div>36</div>
                      <div className="small text-muted">
                        Last Modified: Jan 1, 2015
                      </div>
                    </td>
                    <td className="text-center">
                      <div>1.5</div>
                      <div className="small text-muted">
                        Renewal Site
                      </div>
                    </td>
                    <td className="text-center">
                      <div>33.7</div>
                      <div className="small text-muted">
                        Sunset Soon
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td className="text-center">
                      <div>lululemon.ca</div>
                      <div className="small text-muted">
                        Created: Jan 1, 2015
                      </div>
                    </td>
                    <td>
                      <div className="callout callout-info">
                        <small className="text-muted">New Clients</small>
                        <br/>
                        <strong className="h4">5,986</strong>
                        <div className="chart-wrapper">
                          <Line data={makeSparkLineData(0, brandPrimary)} options={sparklineChartOpts} width={100} height={30}/>
                        </div>
                      </div>
                    </td>
                    <td>
                      <div className="clearfix">
                        <div className="callout callout-danger">
                          <small className="text-muted">Recurring Clients</small>
                          <br/>
                          <strong className="h4">12.3</strong>
                          <div className="chart-wrapper">
                            <Line data={makeSparkLineData(1, brandDanger)} options={sparklineChartOpts} width={100} height={30}/>
                          </div>
                        </div>
                      </div>
                    </td>
                    <td className="text-center">
                      <div>lululemon.ca</div>
                      <div className="small text-muted">
                        Created: Jan 1, 2015
                      </div>
                    </td>
                    <td className="text-center">
                      <div>5.8</div>
                      <div className="small text-muted">
                        Favorite
                      </div>
                    </td>
                    <td className="text-center">
                      <div>4.7</div>
                      <div className="small text-muted">
                        Developing Area
                      </div>
                    </td>
                  </tr>
                  </tbody>
                </Table>
              </CardBody>
            </Card>
          </Col>
        </Row>
      </div>
    )
  }
}
