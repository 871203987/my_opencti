process.env.BACK_END_URL = process.env.BACK_END_URL || 'http://localhost:8082';
process.env.FRONT_END_PORT = process.env.FRONT_END_PORT || '3000';

console.log('Starting OpenCTI Frontend...');
console.log('Backend URL:', process.env.BACK_END_URL);
console.log('Frontend Port:', process.env.FRONT_END_PORT);

require('./builder/dev/dev.js');
