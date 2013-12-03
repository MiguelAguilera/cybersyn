var width = 960,
    height = 800;

var color = d3.scale.category20();

    var force = d3.layout.force()
    .charge(-120)
.linkDistance(300)
    .size([width, height]);

    var svg = d3.select("body").append("svg")
    .attr("width", width)
    .attr("height", height);

    d3.csv("data.csv",function(error,data){
        var nodes = {};
        data.forEach(function(link){
            link.source = nodes[link.source] || (nodes[link.source] = {name:link.source});
            link.target = nodes[link.target] || (nodes[link.target] = {name:link.target});
            link.value = +link.value;
        });
        force
        .nodes(d3.values(nodes))
        .links(data)
        .start();

    var link = svg.selectAll(".link")
        .data(data)
        .enter().append("line")
        .attr("class","link")
        .style("stroke-width", function(d) { return d.value*10; });

    var node = svg.selectAll(".node")
        .data(force.nodes())
        .enter()
        .append("circle")
        .attr("class", "node")
        .attr("r", 5)
        .style("fill", function(d) { return color(d.index); })
        .call(force.drag);

    var texts = svg.selectAll("text.label")
        .data(force.nodes())
        .enter().append("text")
        .attr("class","label")
        .attr("fill","black")
        .text(function(d) { return d.name.replace(/_/gi," ");});
    force.on("tick", function() {
        link.attr("x1", function(d) { return d.source.x; })
        .attr("y1", function(d) { return d.source.y; })
        .attr("x2", function(d) { return d.target.x; })
        .attr("y2", function(d) { return d.target.y; });

    node.attr("cx", function(d) { return d.x; })
        .attr("cy", function(d) { return d.y; });

    texts.attr("transform",function(d){
        return "translate(" +d.x + "," + d.y + ")"});
    });
    });
