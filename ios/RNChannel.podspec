
Pod::Spec.new do |s|
  s.name         = "RNChannel"
  s.version      = "1.0.0"
  s.summary      = "RNChannel"
  s.description  = <<-DESC
                  RNChannel
                   DESC
  s.homepage     = ""
  s.license      = "MIT"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author             = { "author" => "itmnext13@gmail.com" }
  s.platform     = :ios, "7.0"
  s.source       = { :git => "https://github.com/author/RNChannel.git", :tag => "master" }
  s.source_files  = "RNChannel/**/*.{h,m}"
  s.requires_arc = true


  s.dependency "React"
  #s.dependency "others"

end

  