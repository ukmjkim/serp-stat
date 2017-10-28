import React, {Component} from 'react';
import PropTypes from "prop-types"
import { connect } from "react-redux"

import {NavLink} from 'react-router-dom';
import {Badge, Nav, NavItem, NavLink as RsNavLink} from 'reactstrap';
import isExternal from 'is-url-external';
import classNames from 'classnames';

import { fetchSite } from "../../../actions/sites"
import { fetchTags } from "../../../actions/tags"

class TagList extends Component {
  componentDidMount() {
    if (!(this.props.activeSite === undefined || this.props.activeSite.site == null)) {
      this.props.fetchTags(this.props.activeSite.site.id);
    }
  }

  componentWillUpdate(nextProps, nextState) {
    if ((!this.props.activeSite && nextProps.activeSite) ||
        (this.props.activeSite != nextProps.activeSite)) {
      nextProps.fetchTags(nextProps.activeSite.site.id);
    }
  }

  handleClick(e) {
    e.preventDefault();
    e.target.parentElement.classList.toggle('open');
  }

  activeRoute(routeName, props) {
    // return this.props.location.pathname.indexOf(routeName) > -1 ? 'nav-item nav-dropdown open' : 'nav-item nav-dropdown';
    return props.location.pathname.indexOf(routeName) > -1 ? 'nav-item nav-dropdown open' : 'nav-item nav-dropdown';

  }

  // todo Sidebar nav secondLevel
  // secondLevelActive(routeName) {
  //   return this.props.location.pathname.indexOf(routeName) > -1 ? "nav nav-second-level collapse in" : "nav nav-second-level collapse";
  // }

  generateSidebar(site, tags) {
    const header = [{
      name: 'Dashboard',
      url: '/dashboard',
      icon: 'icon-speedometer'
    },
    {
      title: true,
      name: site.title,
      wrapper: {
        element: '',
        attributes: {}
      },
      class: ''
    }];

    const tagMap = (item, key) => {
      return {
        name: item.tag,
        key: item.id,
        url: `/site/${site.id}/tag/${item.id}`,
        icon: 'icon-puzzle'
      }
    };

    const tagList = tags.map((tag, index) => tagMap(tag, index));
    const tail = [
      {
        name: 'Customer Service',
        url: '/customer_service',
        icon: 'icon-cloud-download',
        class: 'mt-auto',
        variant: 'success'
      },
      {
        name: 'What\'s New',
        url: '/whats_new',
        icon: 'icon-layers',
        variant: 'danger'
      }
    ];

    const body = header.concat(tagList);
    const items = body.concat(tail);

    return items;
  }

  render() {
    const { site } = this.props.activeSite;
    const { tags } = this.props.tagsList;
    const props = this.props;
    const activeRoute = this.activeRoute;
    const handleClick = this.handleClick;

    if (site === undefined || (!site)) {
      return <div>Loading....</div>;
    }
    if (!tags) {
      return <div>Loading....</div>;
    }

    const items = this.generateSidebar(site, tags);


    // badge addon to NavItem
    const badge = (badge) => {
      if (badge) {
        const classes = classNames( badge.class );
        return (<Badge className={ classes } color={ badge.variant }>{ badge.text }</Badge>)
      }
    };

    // simple wrapper for nav-title item
    const wrapper = item => { return (item.wrapper && item.wrapper.element ? (React.createElement(item.wrapper.element, item.wrapper.attributes, item.name)): item.name ) };

    // nav list section title
    const title =  (title, key) => {
      const classes = classNames( "nav-title", title.class);
      return (<li key={key} className={ classes }>{wrapper(title)} </li>);
    };

    // nav list divider
    const divider = (divider, key) => (<li key={key} className="divider"></li>);

    // nav item with nav link
    const navItem = (item, key) => {
      const classes = classNames( item.class );
      const variant = classNames( "nav-link", item.variant ? `nav-link-${item.variant}` : "");
      return (
        <NavItem key={key} className={classes}>
          { isExternal(item.url) ?
              <RsNavLink href={item.url} className={variant} active>
                <i className={item.icon}></i>{item.name}{badge(item.badge)}
              </RsNavLink>
            :
              <NavLink to={item.url} className={variant} activeClassName="active">
                <i className={item.icon}></i>{item.name}{badge(item.badge)}
              </NavLink>
          }
        </NavItem>
      )
    };

    // nav dropdown
    const navDropdown = (item, key) => {
      return (
        <li key={key} className={activeRoute(item.url, props)}>
          <a className="nav-link nav-dropdown-toggle" href="#" onClick={handleClick.bind(this)}><i className={item.icon}></i>{item.name}</a>
          <ul className="nav-dropdown-items">
            {navList(item.children)}
          </ul>
        </li>)
    };

    // nav link
    const navLink = (item, idx) =>
      item.title ? title(item, idx) :
      item.divider ? divider(item, idx) :
      item.children ? navDropdown(item, idx)
                    : navItem(item, idx) ;

    // nav list
    const navList = (items) => {
      return items.map( (item, index) => navLink(item, index) );
    };

    // sidebar-nav root
    return (
      <Nav>
        {navList(items)}
      </Nav>
    )
  }
}

TagList.propTypes = {
  fetchTags: PropTypes.func.isRequired,
  tagsList: PropTypes.object.isRequired,
  activeSite: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => {
  return {
    activeSite: state.sites.activeSite,
    tagsList: state.tags.tagsList,
  }
};

const mapDispatchToProps = (dispatch) => {
  return {
    fetchTags: (siteId) => dispatch(fetchTags(siteId))
  }
};

export default connect(mapStateToProps, mapDispatchToProps)(TagList);
