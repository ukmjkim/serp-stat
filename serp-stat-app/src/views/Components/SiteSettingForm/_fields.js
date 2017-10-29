export const FIELDS = new Map([
  ["id", {
    type: 'input',
    label: 'ID for Site',
    validate: (key, value) => {
      return { response: true };
    }
  }],
  ["title", {
    type: 'input',
    label: 'Title for Site',
    validate: (key, value) => {
      if (!value) {
        return { response: false, message: `Enter a ${key}` };
      } else {
        return { response: true };
      }
    }
  }],
  ["url", {
    type: 'input',
    label: 'Url for Site',
    validate: (key, value) => {
      if (!value) {
        return { response: false, message: `Enter a ${key}` };
      } else {
        return { response: true };
      }
    }
  }],
  ["nonRankingValue", {
    type: 'input',
    label: 'Non Ranking Value',
    validate: (key, value) => {
      return { response: true };
    }
  }],
  ["tracking", {
    type: 'checkbox',
    label: 'Tracking',
    validate: (key, value) => {
      return { response: true };
    }
  }],
  ["treatNonRankingAs", {
    type: 'checkbox',
    label: 'Non Ranking Null',
    validate: (key, value) => {
      return { response: true };
    }
  }],
  ["dropWWWPrefix", {
    type: 'checkbox',
    label: 'Drop WWW',
    validate: (key, value) => {
      return { response: true };
    }
  }],
  ["dropDirectories", {
    type: 'checkbox',
    label: 'Drop Directories',
    validate: (key, value) => {
      return { response: true };
    }
  }],
]);
