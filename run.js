try {
    require("source-map-support").install();
} catch(err) {
}
require("./out/goog/bootstrap/nodejs.js");
require("./out/dns_resolver.js");
goog.require("dns_resolver.core");
goog.require("cljs.nodejscli");
