import React, {Component} from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

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
import SiteDashboard from '../../views/SiteDashboard/';
import TagDashboard from '../../views/TagDashboard/';
import Keyword from '../../views/Keyword/';
import Settings from '../../views/Settings/';

import { fetchUser } from "../../actions/user"
import { fetchSites } from "../../actions/sites"
import { fetchSite } from "../../actions/sites"

function matchURI(path, uri) {
  const keys = [];
  const pattern = toRegex(path, keys);
  const match = pattern.exec(uri);
  if (!match) return null;
  const params = Object.create(null);
  for (let i = 1; i < match.length; i++) {
    params[keys[i - 1].name] =
      match[i] !== undefined ? match[i] : undefined;
  }
  return params;
}
class Full extends Component {
  componentDidMount() {
    this.props.fetchUser(1);
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.activeUser.user) {
      if (this.props.activeUser.user == null && nextProps.activeUser.user != null) {
        nextProps.fetchSites(nextProps.activeUser.user.id);
      }
    }
    if (nextProps.sitesList.sites) {
      if (this.props.sitesList.sites == null && nextProps.sitesList.sites != null) {
        if (nextProps.sitesList.sites.length > 0) {
          const resultRe = nextProps.location.pathname.match(/\d+/);
          if (resultRe) {
            const siteId = resultRe[0];
            nextProps.fetchSite(siteId);
          } else {
            nextProps.fetchSite(nextProps.sitesList.sites[0].id);
          }
        }
      }
    }
  }

  render() {
    const { user } = this.props.activeUser;
    if (user === undefined || user == null) {
      return (
        <div className="app">
          Loading...
        </div>
      );
    }
    const { site } = this.props.activeSite;
    if (site === undefined || site == null) {
      return (
        <div className="app">
          Loading...
        </div>
      );
    }
    return (
      <div className="app">
        <Header {...this.props}/>
        <div className="app-body">
          <Sidebar {...this.props}/>
          <main className="main">
            <Breadcrumb />
            <Container fluid>
              <Switch>
                <Route exact path="/dashboard" name="Dashboard" component={Dashboard}/>
                <Route path="/site/:site/tag/:tag" name="TagDashboard" component={TagDashboard}/>
                <Route path="/site/:site/keyword" name="Keyword" component={Keyword}/>
                <Route path="/site/:site/settings" name="Settings" component={Settings}/>
                <Route path="/site/:site" name="SiteDashboard" component={SiteDashboard}/>
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

Sidebar.propTypes = {
    fetchUser: PropTypes.func.isRequired,
    fetchSites: PropTypes.func.isRequired,
    fetchSite: PropTypes.func.isRequired,
    activeUser: PropTypes.object.isRequired,
    sitesList: PropTypes.object.isRequired,
    activeSite: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => {
  return {
    activeUser: state.user.activeUser,
    sitesList: state.sites.sitesList,
    activeSite: state.sites.activeSite,
  }
};

const mapDispatchToProps = (dispatch) => {
  return {
    fetchUser: (id) => dispatch(fetchUser(id)),
    fetchSites: (id) => dispatch(fetchSites(id)),
    fetchSite: (id) => dispatch(fetchSite(id))
  }
};

export default connect(mapStateToProps, mapDispatchToProps)(Full);
