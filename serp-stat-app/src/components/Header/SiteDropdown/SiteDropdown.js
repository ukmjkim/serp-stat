import React, { Component } from "react"
import PropTypes from "prop-types"
import { connect } from "react-redux"
import { Route, Link } from 'react-router-dom';

import { Dropdown, DropdownToggle, DropdownMenu, DropdownItem } from "reactstrap";

import { fetchSites } from "../../../actions/sites"
import { fetchSite } from "../../../actions/sites"
import { resetUpdatedSite } from "../../../actions/sites"

class SiteDropdown extends Component {
  constructor(props) {
    super(props);

    this.toggle = this.toggle.bind(this);
    this.select = this.select.bind(this);

    this.state = {
      dropdownOpen: false,
      value : "Home"
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updatedSite.site) {
      if (this.props.updatedSite.site == null && nextProps.updatedSite.site != null) {
        nextProps.fetchSites(nextProps.activeUser.user.id);
        if (nextProps.activeSite.site) {
          nextProps.fetchSite(nextProps.activeSite.site.id);
        }
      }
    }
  }

  toggle() {
    this.setState({
      dropdownOpen: !this.state.dropdownOpen
    });
  }

  select(e, site) {
    this.setState({
      dropdownOpen: !this.state.dropdownOpen,
      redirect: true
    });
    this.props.fetchSite(site.id);
  }
  render() {
    const { sites } = this.props.sitesList;
    if (sites == null || sites === undefined || sites.length == 0) {
      return (
        <Dropdown isOpen={this.state.dropdownOpen} toggle={this.toggle}>
          <DropdownToggle caret>
            Site List
          </DropdownToggle>
        </Dropdown>
      );
    }

    const mappedSites = sites.map(site => {
        const siteLink = `/site/${site.id}`;
        return (
          <DropdownItem key={site.id}  onClick={ (e) => this.select(e, site) }>
            <Link to={siteLink}>{site.title}</Link>
          </DropdownItem>
        )
      }
    )
    return (
      <Dropdown isOpen={this.state.dropdownOpen} toggle={this.toggle}>
        <DropdownToggle caret>
          Site List
        </DropdownToggle>
        <DropdownMenu>
          {mappedSites}
        </DropdownMenu>
      </Dropdown>
    );
  }
}

SiteDropdown.propTypes = {
    fetchSites: PropTypes.func.isRequired,
    fetchSite: PropTypes.func.isRequired,
    resetUpdatedSite: PropTypes.func.isRequired,
    activeUser: PropTypes.object.isRequired,
    sitesList: PropTypes.object.isRequired,
    activeSite: PropTypes.object.isRequired,
    updatedSite: PropTypes.object.isRequired
};

const mapStateToProps = (state) => {
  return {
    activeUser: state.user.activeUser,
    sitesList: state.sites.sitesList,
    activeSite: state.sites.activeSite,
    updatedSite: state.sites.updatedSite
  }
};

const mapDispatchToProps = (dispatch) => {
  return {
    fetchSites: (userId) => dispatch(fetchSites(userId)),
    fetchSite: (site) => dispatch(fetchSite(site)),
    resetUpdatedSite: () => dispatch(resetUpdatedSite())
  }
};

export default connect(mapStateToProps, mapDispatchToProps)(SiteDropdown);
