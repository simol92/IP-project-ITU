
/* jshint esversion: 6 */

document.getElementById("searchbutton").onclick = () => {
    fetch("/search?q=" + document.getElementById("searchbox").value)
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        if (data.length == 0) {
          document.getElementById("responsesize").innerHTML =
            "<p>No web page contains the query word<p>";
        } else {
          document.getElementById("responsesize").innerHTML =
            "<p>" + data.length + " websites retrieved!<p>";
        }
  
        let results = data
          .map(
            (page) => `<br><li><a href="${page.url}">${page.title}</a></li></br>`
          )
          .join("\n");
        document.getElementById("urllist").innerHTML = `<ul>${results}</ul>`;
      });
  };