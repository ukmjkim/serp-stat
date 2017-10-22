import React from 'react';
import { Route, Link } from 'react-router-dom';
import { Breadcrumb, BreadcrumbItem } from 'reactstrap';
import { routes, findRouteInfo } from '../../routes';

const findRouteName = url => routes[url];

const getPaths = (pathname) => {
  const paths = [];
  const route = findRouteInfo("/");
  if (route != null) {
    paths.push(route);
  }
  if (pathname === '/') return paths;

  pathname.split('/').reduce((prev, curr, index) => {
    const currPath = `${prev}/${curr}`;
    const route = findRouteInfo(currPath);
    if (route != null) {
      paths.push(route);
    }
    return currPath;
  });

  return paths;
};

const BreadcrumbsItem = ({...rest, match}) => {
  const routeInfo = findRouteInfo(match.url);
  const routeName = routeInfo.routeName;

  if (routeName) {
    return (
      match.isExact ?
        (
          <BreadcrumbItem active>{routeName}</BreadcrumbItem>
        ) :
        (
          <BreadcrumbItem>
            <Link to={match.url || ''}>
              {routeName}
            </Link>
          </BreadcrumbItem>
        )
    );
  }
  return null;
};

const Breadcrumbs = ({...rest, location : {pathname}, match}) => {
  const paths = getPaths(pathname);
  const items = paths.map((path, i) => <Route key={i++} path={path.path} component={BreadcrumbsItem}/>);
  return (
    <Breadcrumb>
      {items}
    </Breadcrumb>
  );
};

export default props => (
  <div>
    <Route path="/:path" component={Breadcrumbs} {...props} />
  </div>
);
