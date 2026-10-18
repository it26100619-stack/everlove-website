EVERLOVE WEDDINGS - business version

RUN (IntelliJ): File > Open > pom.xml > Open as Project > Maven panel > wedding > Plugins > jetty > jetty:run
Then open http://localhost:8081

CUSTOMER: register on the site, browse Vendors, press Book now, choose date and guests, press Request booking, then watch My bookings.
ADMIN: log in with admin@wd44.lk / admin123 -> Admin panel (vendors, bookings, couples, plans, reviews, admins).
Sample couple: nimal@mail.com / pass123

CHANGE THE BRAND NAME: search for "Everlove" in WEB-INF/views/store/site-nav.jspf, site-foot.jspf and common/head.jspf.
USE REAL PHOTOS: put hero.jpg in src/main/webapp/images/ (it is shown automatically in front of hero.svg).

DEPLOY: a Dockerfile is included (Tomcat 9). On Render: New > Web Service > connect your GitHub repo > Language: Docker > Instance: Free > add environment variable PORT=8080.
Free hosts erase the data/ files on restart. For real customers use a host with a persistent disk, or move the data to a database.

VENDOR PHOTOS: Admin panel > Vendors > Edit > Vendor photo > Choose file > Upload photo. Photos are saved in data/photos. Without a photo the site shows the category picture.

SECURITY AND QUALITY (added)
- Passwords are stored hashed (salted SHA-256). Sample logins still work: admin@wd44.lk / admin123, nimal@mail.com / pass123.
- Register checks the email is new and the password has 6+ characters.
- Delete and confirm/cancel use POST forms (no state changes by link).
- Session cookie is HttpOnly, security headers are set, branded 404/500 pages.
CUSTOMER: vendor page with reviews, My bookings (cancel, write review after Confirmed), My wedding (date, venue, budget tracker, countdown).
ADMIN: dashboard shows pending bookings with Confirm / Decline buttons.
