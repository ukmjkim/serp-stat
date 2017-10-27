import React, {Component} from 'react';

import classNames from 'classnames';
import SidebarFooter from './../SidebarFooter';
import SidebarForm from './../SidebarForm';
import SidebarHeader from './../SidebarHeader';
import SidebarMinimizer from './../SidebarMinimizer';
import TagList from './TagList';

class Sidebar extends Component {
  render() {
    return (
      <div className="sidebar">
        <SidebarHeader/>
        <SidebarForm/>
        <nav className="sidebar-nav">
          <TagList/>
        </nav>
        <SidebarFooter/>
        <SidebarMinimizer/>
      </div>
    )
  }
}

export default Sidebar;
