import React, {Component} from 'react';
import { Switch, Route, Redirect } from 'react-router-dom';
import { Container } from 'reactstrap';

// components
import Header from '../../components/Header/';
import Sidebar from '../../components/Sidebar/';
import Breadcrumb from '../../components/Breadcrumb/';
import Aside from '../../components/Aside/';
import Footer from '../../components/Footer/';

// Components
import Dashboard from '../../views/Dashboard/';
import Keyword from '../../views/Keyword/';
import Settings from '../../views/Settings/';

export default class Full extends Component {
  constructor() {
    super();
    this.state = {
      siteId: 0,
      entities: [
        {
          key: 1,
          name: "top10_keywords",
          path: "site/:site/tag/:tag"
        },
        {
          key: 2,
          name: "high_revenue",
          path: "site/:site/tag/:tag"
        },
        {
          key: 3,
          name: "seasonal",
          path: "site/:site/tag/:tag"
        },
        {
          key: 4,
          name: "targetting",
          path: "site/:site/tag/:tag"
        },
      ]
    }
  }

  changeSite(siteId) {
    this.setState({siteId});
  }
  render() {
    console.log(this.props);
    const { entities } = this.state;
    const EntityItems = entities.map((entity) => {
      return <Route path={entity.path} name={entity.name} key={entity.key} component={Dashboard} {...entity}/>;
    });

    return (
      <div className="app">
        <Header {...this.props}/>
        <div className="app-body">
          <Sidebar {...this.props}/>
          <main className="main">
            <Breadcrumb />
            <Container fluid>
              <Switch>
                <Route path="dashboard" name="dashboard" component={Dashboard}/>
                {EntityItems}
                <Route path="keyword" name="keyword" component={Keyword}/>
                <Route path="settings" name="settings" component={Settings}/>
                <Redirect from="/" to="/dashboard"/>
              </Switch>
            </Container>
          </main>
          <Aside />
        </div>
        <Footer />
      </div>
    );
  }
}
