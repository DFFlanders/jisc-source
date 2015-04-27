# Sharing Objects Under Repository Control with Everyone #

This is the page where the screencast should go.  The border are expandable, depending on what the best resolution for the screencast should be.  Keep in mind when recording so as to optimise viewability of the visitor to the site.
<a href='Hidden comment: 
This is where the screencast by Jeromy should go.
'></a>

<a href='http://www.youtube.com/watch?feature=player_embedded&v=3LkNlTNHZzE' target='_blank'><img src='http://img.youtube.com/vi/3LkNlTNHZzE/0.jpg' width='600' height=500 /></a>


_The most important findings of the SOURCE project do not come in terms of code or reports, but rather in how the owners (aka stewards) of repositories perceive the use of the resources held within the repository over time and space._

The two significant conclusions of this two year project are:


1. No system is permanent, all repositories will eventually be replaced with better systems/technology/code.

2. Content is only valuable when multiple things use it (e.g. when additional metadata is added)


With that said (if you believe the above findings) you can stop reading, however if you'd like to find out why we came to these conclusions along with the bulk-migration tools we have created to support these conclusions then please do read on.

What follows are some comments on how one might think about the work that was done under this project and how that work might apply to particular problems.  A number of documents related to the project, its progress, demonstration, and final state are available (see the Downloads section).  The source code developed under the project is obtainable via Subversion (see the Source section).  All are encouraged to post comments under either the Wiki or Issues sections.

How the need for interoperability emerges:

The SOURCE project recognizes that quite often a user-facing application and a content management system are developed as a pair.  For example, a project may focus its effort on developing high-quality content and then, naturally, some user-facing application needs to be fielded to expose this content.  All too often, this application is custom built or at least customized significantly.  Similarly, a project might develop an innovative tool, such as a mind mapping application, but will need a reference data set to work on, and so a content system is put in place to meet this need.  This pattern can be described as a 1-to-1 association of consumer of content and producer of content.

In many cases, over time, people want to use the content consumer to work with different a different content system or use the existing content with a different consumer.  This pattern can be described as a many-to-many association.

Interoperability solutions are incremental:

The road from 1-to-1 to many-to-many often passes through the intermediate stage of 1-to-a few.  During this evolutionary stage, projects typically look carefully at using a standard for at accessing content through software drivers or something similar.  Here take as an example printer, camera, or graphics drivers which enable applications to work with many devices through a common interface and using implementations which encapsulate  connection and data exchange details.

Unfortunately, standards solve some of the problem, but tend to be a little too narrowly tailored to be universal.  The SOURCE project chose to look at the Open Knowledge Initiative's Repository interoperability standard as a generic way to cover a number of standards.  Other projects have chosen this same approach or have chosen other standards.

On balance, there are many ways to solve this family of interoperability problems. Solutions tend to emerge in stages or leaps of interoperability towards a universal solution, but not attaining one.  Fortunately, a universal solution is often beyond the needs of any project.  What one should do is look carefully at different standards.  Here is some summary guidance:

**Assume** technologies will change.

**Plan** for minimizing the disruption caused by substituting a new implementation or adding a new implementation to a federation.

**Try** an insulate content consumers from the idiosyncratic protocol and data exchange rules of any particular system through use of an abstraction layer.

**Validate** any design using at least two consumers and producers, preferably developed by separate organizations.

Other projects have benefited from the good work that has come out of SOURCE in interoperability.  Here are some places to look:

[SWORD project](http://www.swordapp.org)

[Visual Understanding Environment (VUE)](http://vue.tufts.edu)

[Open Knowledge Initiative](http://www.okiproject.org)

[IMS Global Learning Consortium](http://www.imsglobal.org)

[Digital Marketplace](http://www.21st-digitalmarketplace.com)

[SoftChalk](http://www.softchalk.com)

