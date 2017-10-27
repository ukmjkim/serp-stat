import React, {Component} from 'react';
import PropTypes from "prop-types"
import { connect } from "react-redux"

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

import { userFetchData } from "../../actions/user"

class Full extends Component {
  componentDidMount() {
    this.props.fetchData(1);
  }

  render() {
    const { user } = this.props;

    if (user === undefined || user.id == null) {
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
                <Route path="/dashboard" name="Dashboard" component={Dashboard}/>
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
    fetchData: PropTypes.func.isRequired,
    user: PropTypes.object.isRequired,
    hasErrored: PropTypes.bool.isRequired,
    isLoading: PropTypes.bool.isRequired
};

const mapStateToProps = (state) => {
  return {
    user: state.user,
    hasErrored: state.userHasErrored,
    isLoading: state.userIsLoading
  }
};

const mapDispatchToProps = (dispatch) => {
  return {
    fetchData: (id) => dispatch(userFetchData(id))
  }
};

export default connect(mapStateToProps, mapDispatchToProps)(Full);
