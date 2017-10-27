import React, {Component} from 'react';
import PropTypes from "prop-types"
import { connect } from "react-redux"

import {NavLink} from 'react-router-dom';
import {Badge, Nav, NavItem, NavLink as RsNavLink} from 'reactstrap';
import isExternal from 'is-url-external';
import classNames from 'classnames';

import { sitesSelected } from "../../../actions/sites"
import { tagsFetchData } from "../../../actions/tags"

class TagList extends Component {
  componentDidMount() {
    if (this.props.sitesSelected) {
      this.props.fetchData(this.props.sitesSelected);
    }
  }

  componentWillUpdate(nextProps, nextState) {
    if ((!this.props.sitesSelected && nextProps.sitesSelected) ||
        (this.props.sitesSelected != nextProps.sitesSelected)) {
      nextProps.fetchData(nextProps.sitesSelected.id);
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

  generateSidebar(props) {
    const header = [{
      name: 'Dashboard',
      url: '/dashboard',
      icon: 'icon-speedometer'
    },
    {
      title: true,
      name: props.sitesSelected.title,
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
        url: '/site/' + props.sitesSelected.id + '/tag/'+item.id,
        icon: 'icon-puzzle'
      }
    };

    const tags = props.tags.map((tag, index) => tagMap(tag, index));
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

    const body = header.concat(tags);
    const items = body.concat(tail);

    return items;
  }

  render() {
    const props = this.props;
    const activeRoute = this.activeRoute;
    const handleClick = this.handleClick;

    if (!props.hasFetched) {
      return <div>Loading....</div>;
    }

    const items = this.generateSidebar(props);


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
    sitesSelected: PropTypes.object,
    fetchData: PropTypes.func.isRequired,
    tags: PropTypes.array.isRequired,
    hasErrored: PropTypes.bool.isRequired,
    hasFetched: PropTypes.bool.isRequired,
    isLoading: PropTypes.bool.isRequired
};

const mapStateToProps = (state) => {
  return {
    sitesSelected: state.sitesSelected,
    tags: state.tags,
    hasErrored: state.tagsHasErrored,
    hasFetched: state.tagsHasFetched,
    isLoading: state.tagsIsLoading
  }
};

const mapDispatchToProps = (dispatch) => {
  return {
    fetchData: (siteId) => dispatch(tagsFetchData(siteId))
  }
};

export default connect(mapStateToProps, mapDispatchToProps)(TagList);
