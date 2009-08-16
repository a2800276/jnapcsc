
PACKAGE = "de.kuriositaet.iso7816.apdu"

TEMPLATE = <<END
// Copyright 2009 by Tim Becker (tim.becker@kuriostaet.de)
// MIT License, for details, see the LICENSE file accompaning
// this distribution

// The base implemenation of this class was generate automatically,
// please see src/ruby/make_apdu.rb

package #{PACKAGE};

import de.kuriositaet.iso7816.APDU;
/**
 * Class implementing the %s command defined in ISO 7816-4
 *
 */
public class %s extends APDU {
	public %s () {
		super("%s", (byte)0x00, (byte)0x%s);
  }
}
END

def create_class_template name, ins
  jn = java_name(name)
  java = TEMPLATE % [name, jn, jn, name, ins]
  Dir.mkdir("tmp") unless File.exist?("tmp")
  File.open("tmp/#{jn}.java", "w") {|file|
    file.puts(java)
  }
end

def java_name name
  java_name = name.gsub(/(\s|\W)+/, '_')
  java_name.gsub(/_$/, '')
end
# Generate Java APDU templates from definition file.
File.open("src/java/de/kuriositaet/iso7816/apdu/iso_apdus.txt").each {|line|
  
  next if line.strip =~ /^$/
  
  if line =~ /^([^']*) '([^']*)'/
    create_class_template($1, $2)
  else
    puts "invalid"
    puts line
  end
}

